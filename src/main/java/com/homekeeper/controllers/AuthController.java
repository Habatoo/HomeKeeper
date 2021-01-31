package com.homekeeper.controllers;

import com.homekeeper.models.Token;
import com.homekeeper.models.User;
import com.homekeeper.payload.request.LoginRequest;
import com.homekeeper.payload.request.PasswordRequest;
import com.homekeeper.payload.response.PasswordResponse;
import com.homekeeper.payload.response.JwtResponse;
import com.homekeeper.payload.response.MessageResponse;
import com.homekeeper.repository.TokenRepository;
import com.homekeeper.repository.UserRepository;
import com.homekeeper.security.jwt.JwtUtils;
import com.homekeeper.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер доступа. Реализваны методы login, logout.
 * @version 0.013
 * @author habatoo
 *
 * @method logoutUser - при http ?? get запросе по адресу .../api/auth/logout
 * @param "authentication" - запрос на доступ с параметрами user login+password.
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;

    @Value("${homekeeper.app.secretKey}")
    private String secretKey;

    @Value("${homekeeper.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * @method authenticateUser - при http post запросе по адресу .../api/auth/login
     * @param loginRequest - запрос на доступ с параметрами user login+password.
     * возвращает
     * @return {@code ResponseEntity ответ}
     * @see LoginRequest
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        Token token = new Token(jwt, userRepository.findByUserName(authentication.getName()).get());
        token.setActive(true);

        Date date = new Date();
        LocalDateTime createDate = Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime expireDate = Instant.ofEpochMilli(date.getTime() + jwtExpirationMs)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        token.setCreationDate(createDate);
        token.setExpiryDate (expireDate);

        token.setUser(userRepository.findByUserName(authentication.getName()).get());
        tokenRepository.save(token);

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    /**
     * @method logoutUser - при http post запросе по адресу .../api/auth/logout
     * @param request - запрос на выход с параметрами user login+password + токен jwt.
     * возвращает
     * @return {@code ResponseEntity ответ}
     * @see LoginRequest
     */
    @GetMapping("/logout")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        String jwt = headerAuth.substring(7, headerAuth.length());

        Token unActiveToken = tokenRepository.findByToken(jwt);
        unActiveToken.setActive(false);
        tokenRepository.save(unActiveToken);

        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("You are logout."));
    }

    /**
     * @method resetPassword - при http post запросе по адресу .../api/auth/reset
     * @param passwordRequest - запрос на сброс пароля с параметрами user login+sekretKey (хранится в env).
     * возвращает
     * @return {@code ResponseEntity ответ}
     * @see LoginRequest
     */
    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody PasswordRequest passwordRequest) {

        if (!userRepository.existsByUserName(
                passwordRequest.getUserName()
        )) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username does not exist!"));
        }

        if (!secretKey.equals(passwordRequest.getSecretKey())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: SecretKey does not valid!"));
        }

        User user = userRepository.findByUserName(passwordRequest.getUserName()).get();
        user.setPassword(encoder.encode(passwordRequest.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(new PasswordResponse(
                passwordRequest.getUserName(),
                passwordRequest.getPassword()
                ));
    }

}
