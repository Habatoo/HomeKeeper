package com.homekeeper.payload.response;

public class PaymentResponse {

    private String message;

    private Long id;

    private double waterColdValueCurrentMonth;
    private String waterColdSum;

    private double waterWarmValueCurrentMonth;
    private String waterWarmSum;

    private double electricityValueCurrentMonth;
    private String electricitySum;

    private double internetValueCurrentMonth;
    private String internetSum;

    private double waterOutValueCurrentMonth;
    private String waterOutSum;

    private String rentRateSum;
    private String rentSum;

    public PaymentResponse(String message,Long id, double waterColdValueCurrentMonth, String waterColdSum,
                           double waterWarmValueCurrentMonth, String waterWarmSum,
                           double electricityValueCurrentMonth, String electricitySum,
                           double internetValueCurrentMonth, String internetSum,
                           double waterOutValueCurrentMonth, String waterOutSum,
                           String rentRateSum, String rentSum) {
        this.message = message;
        this.id = id;
        this.waterColdValueCurrentMonth = waterColdValueCurrentMonth;
        this.waterColdSum = waterColdSum;
        this.waterWarmValueCurrentMonth = waterWarmValueCurrentMonth;
        this.waterWarmSum = waterWarmSum;
        this.electricityValueCurrentMonth = electricityValueCurrentMonth;
        this.electricitySum = electricitySum;
        this.internetValueCurrentMonth = internetValueCurrentMonth;
        this.internetSum = internetSum;
        this.waterOutValueCurrentMonth = waterOutValueCurrentMonth;
        this.waterOutSum = waterOutSum;
        this.rentRateSum = rentRateSum;
        this.rentSum = rentSum;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

}
