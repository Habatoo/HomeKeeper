package com.homekeeper.models;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import javax.persistence.*;

//@Entity
//@Table(name = "tariffs")
//@ToString(of = {
//        "id",
//        "tariffName",
//        "tariffValue",
//        "tariffStartDate",
//        "tariffEndDate"
//})
//@EqualsAndHashCode(of = {"id"})
public class Tariff {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ETariffValues tariffName;

    private double tariffValue;

    @Column(updatable = false)
    private LocalDateTime tariffStartDate;

    @Column(updatable = false)
    private LocalDateTime tariffEndDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ETariffValues getTariffName() {
        return tariffName;
    }

    public void setTariffName(ETariffValues tariffName) {
        this.tariffName = tariffName;
    }

    public double getTariffValue() {
        return tariffValue;
    }

    public void setTariffValue(double tariffValue) {
        this.tariffValue = tariffValue;
    }

    public LocalDateTime getTariffStartDate() {
        return tariffStartDate;
    }

    public void setTariffStartDate(LocalDateTime tariffStartDate) {
        this.tariffStartDate = tariffStartDate;
    }

    public LocalDateTime getTariffEndDate() {
        return tariffEndDate;
    }

    public void setTariffEndDate(LocalDateTime tariffEndDate) {
        this.tariffEndDate = tariffEndDate;
    }
}
