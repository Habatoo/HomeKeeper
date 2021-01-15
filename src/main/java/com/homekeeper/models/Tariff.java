package com.homekeeper.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Currency;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Тарифы на коммунальные услуги с историей изменений величины татифов.
 *  * @version 0.013
 *  * @author habatoo
 *
 */
@Entity
@Table(name = "tariffs")
@ToString(of = {"id",
        "waterColdRate",
        "waterHotRate",
        "electricityRate",
        "internetRate",
        "rentRate",
        "dateRateChange"
})
@EqualsAndHashCode(of = {"id"})
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double waterColdRate;
    private double waterHotRate;
    private double electricityRate;
    private double internetRate;
    private double rentRate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateRateChange;

    public Tariff() {
    }

    /**
     * Конструктор тарифов.
     * @param waterColdRate
     * @param waterHotRate
     * @param electricityRate
     * @param internetRate
     * @param rentRate
     */
    public Tariff(double waterColdRate, double waterHotRate, double electricityRate, double internetRate, double rentRate) {
        // BigDecimal.ROUND_DOWN);
        this.waterColdRate = waterColdRate;
        this.waterHotRate = waterHotRate;
        this.electricityRate = electricityRate;
        this.internetRate = internetRate;
        this.rentRate = rentRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getWaterColdRate() {
        return waterColdRate;
    }

    public void setWaterColdRate(double waterColdRate) {
        this.waterColdRate = waterColdRate;
    }

    public double getWaterHotRate() {
        return waterHotRate;
    }

    public void setWaterHotRate(double waterHotRate) {
        this.waterHotRate = waterHotRate;
    }

    public double getElectricityRate() {
        return electricityRate;
    }

    public void setElectricityRate(double electricityRate) {
        this.electricityRate = electricityRate;
    }

    public double getInternetRate() {
        return internetRate;
    }

    public void setInternetRate(double internetRate) {
        this.internetRate = internetRate;
    }

    public double getRentRate() {
        return rentRate;
    }

    public void setRentRate(double rentRate) {
        this.rentRate = rentRate;
    }

    public LocalDateTime getDateRateChange() {
        return dateRateChange;
    }

    public void setDateRateChange(LocalDateTime dateRateChange) {
        this.dateRateChange = dateRateChange;
    }
}
