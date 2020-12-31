package com.homekeeper.repository;

import com.homekeeper.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByLastname(String lastName);
//
//    Boolean existsByLastname(String lastName);
//    Boolean existsByEmail(String userEmail);

}
