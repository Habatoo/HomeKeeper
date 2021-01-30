package com.homekeeper.repository;

import com.homekeeper.models.User;
import com.homekeeper.models.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {
    Optional<UserBalance> findById(Long Id);
    Optional<UserBalance> findByUser(User user);
    Optional<UserBalance> findFirstByUserOrderByBalanceDateDesc(User user);

}
