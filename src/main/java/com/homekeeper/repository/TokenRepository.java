package com.homekeeper.repository;

import com.homekeeper.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findById(Long id);
//    Optional<Token> findByActive(boolean active);
//    Optional<Token> findByToken(String token);
//    Boolean existsByToken(String token);
}
