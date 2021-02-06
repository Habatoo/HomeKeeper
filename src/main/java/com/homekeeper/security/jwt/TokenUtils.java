package com.homekeeper.security.jwt;

import com.homekeeper.models.Token;
import com.homekeeper.payload.response.JwtResponse;
import com.homekeeper.repository.TokenRepository;
import com.homekeeper.repository.UserRepository;
import com.homekeeper.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TokenUtils {
    @Value("${homekeeper.app.jwtSecret}")
    private String jwtSecret;

    @Value("${homekeeper.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    public void makeToken(String userName, String strToken) {
        Token token = new Token(strToken, userRepository.findByUserName(userName).get());
        token.setActive(true);
        Date date = new Date();
        LocalDateTime createDate = Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime expireDate = Instant.ofEpochMilli(date.getTime() + jwtExpirationMs)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        token.setCreationDate(createDate);
        token.setExpiryDate(expireDate);

        token.setUser(userRepository.findByUserName(userName).get());
        tokenRepository.save(token);
    }

    public JwtResponse makeAuth(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }
}
