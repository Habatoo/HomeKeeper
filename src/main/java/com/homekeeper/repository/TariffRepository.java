package com.homekeeper.repository;

import com.homekeeper.models.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TariffRepository extends JpaRepository<Tariff, Long> {

    Optional<Tariff> findById(Long id);

    Optional<Tariff> findFirstByOrderByDateRateChangeDesc();

}
