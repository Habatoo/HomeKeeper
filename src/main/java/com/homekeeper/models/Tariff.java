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
        "waterOutRate",
        "dateRateChange"
})
@EqualsAndHashCode(of = {"id"})
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String waterColdRate;
    private String waterHotRate;
    private String electricityRate;
    private String internetRate;
    private String waterOutRate;
    private String rentRate;

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
    public Tariff(String waterColdRate, String waterHotRate, String electricityRate, String internetRate, String waterOutRate, String rentRate) {
        this.waterColdRate = waterColdRate;
        this.waterHotRate = waterHotRate;
        this.electricityRate = electricityRate;
        this.internetRate = internetRate;
        this.waterOutRate = waterOutRate;
        this.rentRate = rentRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWaterColdRate() {
        return waterColdRate;
    }

    public void setWaterColdRate(String waterColdRate) {
        this.waterColdRate = waterColdRate;
    }

    public String getWaterHotRate() {
        return waterHotRate;
    }

    public void setWaterHotRate(String waterHotRate) {
        this.waterHotRate = waterHotRate;
    }

    public String getElectricityRate() {
        return electricityRate;
    }

    public void setElectricityRate(String electricityRate) {
        this.electricityRate = electricityRate;
    }

    public String getInternetRate() {
        return internetRate;
    }

    public void setInternetRate(String internetRate) {
        this.internetRate = internetRate;
    }

    public String getRentRate() {
        return rentRate;
    }

    public void setRentRate(String rentRate) {
        this.rentRate = rentRate;
    }

    public LocalDateTime getDateRateChange() {
        return dateRateChange;
    }

    public void setDateRateChange(LocalDateTime dateRateChange) {
        this.dateRateChange = dateRateChange;
    }

    public String getWaterOutRate() {
        return waterOutRate;
    }

    public void setWaterOutRate(String waterOutRate) {
        this.waterOutRate = waterOutRate;
    }
}
