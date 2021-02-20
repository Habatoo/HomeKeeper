package com.homekeeper.payload.request;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class AddMonthDataRequest {

    @NotBlank
    private double waterColdValueCurrentMonth;
    @NotBlank
    private String waterColdSum;

    @NotBlank
    private double waterWarmValueCurrentMonth;
    @NotBlank
    private String waterWarmSum;

    @NotBlank
    private double electricityValueCurrentMonth;
    @NotBlank
    private String electricitySum;

    @NotBlank
    private double internetValueCurrentMonth;
    @NotBlank
    private String internetSum;

    @NotBlank
    private double waterOutValueCurrentMonth;
    @NotBlank
    private String waterOutSum;

    @NotBlank
    private String rentRateSum;
    @NotBlank
    private String rentSum;

    private int homeMates;

    private LocalDateTime paymentDate;

    public double getWaterColdValueCurrentMonth() {
        return waterColdValueCurrentMonth;
    }

    public void setWaterColdValueCurrentMonth(double waterColdValueCurrentMonth) {
        this.waterColdValueCurrentMonth = waterColdValueCurrentMonth;
    }

    public String getWaterColdSum() {
        return waterColdSum;
    }

    public void setWaterColdSum(String waterColdSum) {
        this.waterColdSum = waterColdSum;
    }

    public double getWaterWarmValueCurrentMonth() {
        return waterWarmValueCurrentMonth;
    }

    public void setWaterWarmValueCurrentMonth(double waterWarmValueCurrentMonth) {
        this.waterWarmValueCurrentMonth = waterWarmValueCurrentMonth;
    }

    public String getWaterWarmSum() {
        return waterWarmSum;
    }

    public void setWaterWarmSum(String waterWarmSum) {
        this.waterWarmSum = waterWarmSum;
    }

    public double getElectricityValueCurrentMonth() {
        return electricityValueCurrentMonth;
    }

    public void setElectricityValueCurrentMonth(double electricityValueCurrentMonth) {
        this.electricityValueCurrentMonth = electricityValueCurrentMonth;
    }

    public String getElectricitySum() {
        return electricitySum;
    }

    public void setElectricitySum(String electricitySum) {
        this.electricitySum = electricitySum;
    }

    public double getInternetValueCurrentMonth() {
        return internetValueCurrentMonth;
    }

    public void setInternetValueCurrentMonth(double internetValueCurrentMonth) {
        this.internetValueCurrentMonth = internetValueCurrentMonth;
    }

    public String getInternetSum() {
        return internetSum;
    }

    public void setInternetSum(String internetSum) {
        this.internetSum = internetSum;
    }

    public double getWaterOutValueCurrentMonth() {
        return waterOutValueCurrentMonth;
    }

    public void setWaterOutValueCurrentMonth(double waterOutValueCurrentMonth) {
        this.waterOutValueCurrentMonth = waterOutValueCurrentMonth;
    }

    public String getWaterOutSum() {
        return waterOutSum;
    }

    public void setWaterOutSum(String waterOutSum) {
        this.waterOutSum = waterOutSum;
    }

    public String getRentRateSum() {
        return rentRateSum;
    }

    public void setRentRateSum(String rentRateSum) {
        this.rentRateSum = rentRateSum;
    }

    public String getRentSum() {
        return rentSum;
    }

    public void setRentSum(String rentSum) {
        this.rentSum = rentSum;
    }

    public int getHomeMates() {
        return homeMates;
    }

    public void setHomeMates(int homeMates) {
        this.homeMates = homeMates;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

}
