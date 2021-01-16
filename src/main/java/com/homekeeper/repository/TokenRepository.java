package com.homekeeper.repository;

import com.homekeeper.models.Token;
import com.homekeeper.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    // Optional<Token> findByUserId(String userId);
    // Optional<Token> findById(Long id);
}
