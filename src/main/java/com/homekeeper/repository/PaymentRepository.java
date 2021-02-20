package com.homekeeper.repository;

import com.homekeeper.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findById(Long id);

    Payment findByPaymentDate (LocalDateTime paymentDate);

    Optional<Payment> findFirstByOrderByPaymentDateAsc();

}
