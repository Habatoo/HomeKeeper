package com.homekeeper.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.homekeeper.config.Money;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Модель платежей. Записывается в БД в таблицу с имененм payments.
 * Платежи за коммунальные услуги по месяцам, с историей.
 *  @version 0.013
 *  @author habatoo
 * Переменная paymentDate содержит дату оплаты.
 *
 */
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
        "rentRateSum",
        "rentSum",
        "homeMates",
        "paymentDate"
})
@EqualsAndHashCode(of = {"id"})
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private int homeMates;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "payments_tariffs",
            joinColumns = @JoinColumn(name = "payment_id"),
            inverseJoinColumns = @JoinColumn(name = "tariff_id"))
    private Set<Tariff> tariffs = new HashSet<>();

    public Payment() {
    }

    /**
     *
     * @param waterColdValueCurrentMonth показания счетчика холодной воды
     * @param waterColdSum итоговая сумма оплаты за холодную воду, рублей.копеек.
     * @param waterWarmValueCurrentMonth показания счетчика холодной воды
     * @param waterWarmSum итоговая сумма оплаты за холодную воду, рублей.копеек.
     * @param electricityValueCurrentMonth показания счетчика холодной воды
     * @param electricitySum итоговая сумма оплаты за холодную воду, рублей.копеек.
     * @param internetValueCurrentMonth показания счетчика холодной воды
     * @param internetSum итоговая сумма оплаты за холодную воду, рублей.копеек.
     * @param waterOutValueCurrentMonth показания счетчика холодной воды
     * @param waterOutSum итоговая сумма оплаты за холодную воду, рублей.копеек.
     * @param rentRateSum итоговая сумма квартплаты, рублей.копеек.
     * @param rentSum итоговая сумма аренды, рублей.копеек.
     * @param homeMates количество проживающих.
     */
    public Payment(
            double waterColdValueCurrentMonth,
            String waterColdSum,
            double waterWarmValueCurrentMonth,
            String waterWarmSum,
            double electricityValueCurrentMonth,
            String electricitySum,
            double internetValueCurrentMonth,
            String internetSum,
            double waterOutValueCurrentMonth,
            String waterOutSum,
            String rentRateSum,
            String rentSum,
            int homeMates) {
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
        this.homeMates = homeMates;
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

    public Set<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(Set<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

}
