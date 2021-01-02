package com.homekeeper.repository;

import com.homekeeper.models.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
}
