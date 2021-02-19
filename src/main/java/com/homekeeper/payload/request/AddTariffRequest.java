package com.homekeeper.payload.request;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class AddTariffRequest {

    @NotBlank
    private String waterColdRate;

    @NotBlank
    private String waterHotRate;

    @NotBlank
    private String electricityRate;

    @NotBlank
    private String internetRate;

    @NotBlank
    private String waterOutRate;

    @NotBlank
    private String rentRate;

    private LocalDateTime dateRateChange;

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
