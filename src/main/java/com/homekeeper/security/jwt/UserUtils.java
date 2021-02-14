package com.homekeeper.security.jwt;

import com.homekeeper.models.User;
import com.homekeeper.payload.response.MessageResponse;
import com.homekeeper.repository.TokenRepository;
import com.homekeeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {
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

    @Autowired
    PasswordEncoder encoder;

    /**
     * Проверяет username и email на уникальность и отсуствие аналогов в существующей базе
     * @param user - данные пользователя для изменений
     * @param userFromDb - данные пользователя с дб
     */
    public ResponseEntity<?>  checkUserNameAndEmail(User user, User userFromDb) {
        if (!(user.getUserName().equals(userFromDb.getUserName())) & (userRepository.existsByUserName(user.getUserName()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (!(user.getUserEmail().equals(userFromDb.getUserEmail())) & (userRepository.existsByUserEmail(user.getUserEmail()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        userFromDb.setUserName(user.getUserName());
        userFromDb.setUserEmail(user.getUserEmail());
        userFromDb.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(userFromDb);
        return ResponseEntity.ok(new MessageResponse("User data was update successfully!"));

    }
}
