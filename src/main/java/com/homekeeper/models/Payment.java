package com.homekeeper.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.time.LocalDateTime;

//@Entity
//@Table(name = "payments")
//@ToString(of = {"id",
//        "waterColdValueCurrentMonth",
//        "waterColdSum",
//        "waterWarmValueCurrentMonth",
//        "waterWarmSum",
//        "electricityValueCurrentMonth",
//        "electricitySum",
//        "waterOutValueCurrentMonth",
//        "waterOutSum",
//        "rentSum",
//        "homemates",
//        "paymentDate"
//})
//@EqualsAndHashCode(of = {"id"})
public class Payment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double waterColdValueCurrentMonth;
    private double waterColdSum;

    private double waterWarmValueCurrentMonth;
    private double waterWarmSum;

    private double electricityValueCurrentMonth;
    private double electricitySum;

    private double waterOutValueCurrentMonth;
    private double waterOutSum;

    private double rentSum;

    private int homemates;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

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

    public double getWaterColdSum() {
        return waterColdSum;
    }

    public void setWaterColdSum(double waterColdSum) {
        this.waterColdSum = waterColdSum;
    }

    public double getWaterWarmValueCurrentMonth() {
        return waterWarmValueCurrentMonth;
    }

    public void setWaterWarmValueCurrentMonth(double waterWarmValueCurrentMonth) {
        this.waterWarmValueCurrentMonth = waterWarmValueCurrentMonth;
    }

    public double getWaterWarmSum() {
        return waterWarmSum;
    }

    public void setWaterWarmSum(double waterWarmSum) {
        this.waterWarmSum = waterWarmSum;
    }

    public double getElectricityValueCurrentMonth() {
        return electricityValueCurrentMonth;
    }

    public void setElectricityValueCurrentMonth(double electricityValueCurrentMonth) {
        this.electricityValueCurrentMonth = electricityValueCurrentMonth;
    }

    public double getElectricitySum() {
        return electricitySum;
    }

    public void setElectricitySum(double electricitySum) {
        this.electricitySum = electricitySum;
    }

    public double getWaterOutValueCurrentMonth() {
        return waterOutValueCurrentMonth;
    }

    public void setWaterOutValueCurrentMonth(double waterOutValueCurrentMonth) {
        this.waterOutValueCurrentMonth = waterOutValueCurrentMonth;
    }

    public double getWaterOutSum() {
        return waterOutSum;
    }

    public void setWaterOutSum(double waterOutSum) {
        this.waterOutSum = waterOutSum;
    }

    public double getRentSum() {
        return rentSum;
    }

    public void setRentSum(double rentSum) {
        this.rentSum = rentSum;
    }

    public int getHomemates() {
        return homemates;
    }

    public void setHomemates(int homemates) {
        this.homemates = homemates;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}
