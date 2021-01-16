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
    private Money waterColdSum;

    private double waterWarmValueCurrentMonth;
    private Money waterWarmSum;

    private double electricityValueCurrentMonth;
    private Money electricitySum;

    private double internetValueCurrentMonth;
    private Money internetSum;

    private double waterOutValueCurrentMonth;
    private Money waterOutSum;

    private Money rentRateSum;
    private Money rentSum;

    private int homeMates;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "payment_tariffs",
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
            Money waterColdSum,
            double waterWarmValueCurrentMonth,
            Money waterWarmSum,
            double electricityValueCurrentMonth,
            Money electricitySum,
            double internetValueCurrentMonth,
            Money internetSum,
            double waterOutValueCurrentMonth,
            Money waterOutSum,
            Money rentRateSum,
            Money rentSum,
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

    public Money getWaterColdSum() {
        return waterColdSum;
    }

    public void setWaterColdSum(Money waterColdSum) {
        this.waterColdSum = waterColdSum;
    }

    public double getWaterWarmValueCurrentMonth() {
        return waterWarmValueCurrentMonth;
    }

    public void setWaterWarmValueCurrentMonth(double waterWarmValueCurrentMonth) {
        this.waterWarmValueCurrentMonth = waterWarmValueCurrentMonth;
    }

    public Money getWaterWarmSum() {
        return waterWarmSum;
    }

    public void setWaterWarmSum(Money waterWarmSum) {
        this.waterWarmSum = waterWarmSum;
    }

    public double getElectricityValueCurrentMonth() {
        return electricityValueCurrentMonth;
    }

    public void setElectricityValueCurrentMonth(double electricityValueCurrentMonth) {
        this.electricityValueCurrentMonth = electricityValueCurrentMonth;
    }

    public Money getElectricitySum() {
        return electricitySum;
    }

    public void setElectricitySum(Money electricitySum) {
        this.electricitySum = electricitySum;
    }

    public double getInternetValueCurrentMonth() {
        return internetValueCurrentMonth;
    }

    public void setInternetValueCurrentMonth(double internetValueCurrentMonth) {
        this.internetValueCurrentMonth = internetValueCurrentMonth;
    }

    public Money getInternetSum() {
        return internetSum;
    }

    public void setInternetSum(Money internetSum) {
        this.internetSum = internetSum;
    }

    public double getWaterOutValueCurrentMonth() {
        return waterOutValueCurrentMonth;
    }

    public void setWaterOutValueCurrentMonth(double waterOutValueCurrentMonth) {
        this.waterOutValueCurrentMonth = waterOutValueCurrentMonth;
    }

    public Money getWaterOutSum() {
        return waterOutSum;
    }

    public void setWaterOutSum(Money waterOutSum) {
        this.waterOutSum = waterOutSum;
    }

    public Money getRentRateSum() {
        return rentRateSum;
    }

    public void setRentRateSum(Money rentRateSum) {
        this.rentRateSum = rentRateSum;
    }

    public Money getRentSum() {
        return rentSum;
    }

    public void setRentSum(Money rentSum) {
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
