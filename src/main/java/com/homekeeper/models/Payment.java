package com.homekeeper.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "payments")
@ToString(of = {"id",
        "waterColdValueCurrentMonth",
        "waterColdSum",
        "waterWarmValueCurrentMonth",
        "waterWarmSum",
        "electricityValueCurrentMonth",
        "electricitySum",
        "internetValueCurrentMonth",
        "internetSum",
        "waterOutValueCurrentMonth",
        "waterOutSum",
        "rentValueMonth",
        "rentSum",
        "homemates",
        "paymentDate"
})
@EqualsAndHashCode(of = {"id"})
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double waterColdValueCurrentMonth;
    private double waterColdSum;

    private double waterWarmValueCurrentMonth;
    private double waterWarmSum;

    private double electricityValueCurrentMonth;
    private double electricitySum;

    private double internetValueCurrentMonth;
    private double internetSum;

    private double waterOutValueCurrentMonth;
    private double waterOutSum;

    private double rentValueMonth;
    private double rentSum;

    private int homemates;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "payment_tariffs",
            joinColumns = @JoinColumn(name = "payment_id"),
            inverseJoinColumns = @JoinColumn(name = "tariff_id"))
    private Set<Tariff> tariffs = new HashSet<>();

    public Payment() {

    }

    public Payment(double waterColdValueCurrentMonth, double waterColdSum, double waterWarmValueCurrentMonth, double waterWarmSum, double electricityValueCurrentMonth, double electricitySum, double internetValueCurrentMonth, double internetSum, double waterOutValueCurrentMonth, double waterOutSum, double rentValueMonth, double rentSum, int homemates) {
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
        this.rentValueMonth = rentValueMonth;
        this.rentSum = rentSum;
        this.homemates = homemates;
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

    public double getInternetValueCurrentMonth() {
        return internetValueCurrentMonth;
    }

    public void setInternetValueCurrentMonth(double internetValueCurrentMonth) {
        this.internetValueCurrentMonth = internetValueCurrentMonth;
    }

    public double getInternetSum() {
        return internetSum;
    }

    public void setInternetSum(double internetSum) {
        this.internetSum = internetSum;
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

    public double getRentValueMonth() {
        return rentValueMonth;
    }

    public void setRentValueMonth(double rentValueMonth) {
        this.rentValueMonth = rentValueMonth;
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

    public Set<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(Set<Tariff> tariffs) {
        this.tariffs = tariffs;
    }
}
