package com.homekeeper.security.jwt;

import com.homekeeper.models.Token;
import com.homekeeper.repository.TokenRepository;
import com.homekeeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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
}
