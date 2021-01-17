package com.homekeeper.controllers;

import com.homekeeper.models.Token;
import com.homekeeper.models.User;
import com.homekeeper.payload.request.LoginRequest;
import com.homekeeper.payload.response.JwtResponse;
import com.homekeeper.payload.response.MessageResponse;
import com.homekeeper.repository.TokenRepository;
import com.homekeeper.repository.UserRepository;
import com.homekeeper.security.jwt.JwtUtils;
import com.homekeeper.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Контроллер доступа. Реализваны методы login, logout TODO.
 * @version 0.013
 * @author habatoo
 *
 * @method logoutUser - при http ?? get запросе по адресу .../api/auth/logout TODO
 * @param "authentication" - запрос на доступ с параметрами user login+password. TODO
 * @see Authentication ???  TODO
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
        token.setCreationDate(LocalDateTime.now());
        token.setUser(userRepository.findByUserName(authentication.getName()).get());
        tokenRepository.save(token);

//        System.out.println("TOKEN: " + jwt);
//        System.out.println("TOKEN: " + token);

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(Authentication authentication) {
//        String jwt = jwtUtils.generateJwtToken(authentication);
//        User user = userRepository.findByUserName(authentication.getName()).get();
        //        System.out.println("TOKEN: " + jwt);
        //        System.out.println("TOKEN: " + token);
        //Token token = Token(jwt, user);
        //tokenRepository.delete();
        return (ResponseEntity<?>) ResponseEntity
                .badRequest()
                .body(new MessageResponse("You are logout."));
    }

}
