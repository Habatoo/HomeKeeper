package com.homekeeper.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.homekeeper.config.Money;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Модель тарифов. Записывается в БД в таблицу с имененм tariffs.
 * Тарифы на коммунальные услуги с историей изменений величины тарифов.
 *  * @version 0.013
 *  * @author habatoo
 * Актуальные тарифы всегда находятся в таблице в строке с максимальным id,
 * переменная dateRateChange содержит последнюю дату актуализации тарифов.
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

    private Money waterColdRate;
    private Money waterHotRate;
    private Money electricityRate;
    private Money internetRate;
    private Money rentRate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateRateChange;

    public Tariff() {
    }

    /**
     * Конструктор тарифов.
     * @param waterColdRate тариф на холодную воду, рублей.копеек.
     * @param waterHotRate тариф на горячую воду, рублей.копеек.
     * @param electricityRate тариф на электричество, рублей.копеек.
     * @param internetRate тарф на интернет, рублей.копеек.
     * @param rentRate тариф на квартплату, рублей.копеек.
     */
    public Tariff(Money waterColdRate, Money waterHotRate, Money electricityRate, Money internetRate, Money rentRate) {
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

    public Money getWaterColdRate() {
        return waterColdRate;
    }

    public void setWaterColdRate(Money waterColdRate) {
        this.waterColdRate = waterColdRate;
    }

    public Money getWaterHotRate() {
        return waterHotRate;
    }

    public void setWaterHotRate(Money waterHotRate) {
        this.waterHotRate = waterHotRate;
    }

    public Money getElectricityRate() {
        return electricityRate;
    }

    public void setElectricityRate(Money electricityRate) {
        this.electricityRate = electricityRate;
    }

    public Money getInternetRate() {
        return internetRate;
    }

    public void setInternetRate(Money internetRate) {
        this.internetRate = internetRate;
    }

    public Money getRentRate() {
        return rentRate;
    }

    public void setRentRate(Money rentRate) {
        this.rentRate = rentRate;
    }

    public LocalDateTime getDateRateChange() {
        return dateRateChange;
    }

    public void setDateRateChange(LocalDateTime dateRateChange) {
        this.dateRateChange = dateRateChange;
    }
}
