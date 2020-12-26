package com.homekeeper.models;

import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

public class Payment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double waterColdValueCurrentMonth;
    private double waterColdSum;

    private double waterWarmValueCurrentMonth;
    private double waterWarmSum;

    private double electricityValueCurrentMonth;
    private double electricitySum;

    private double waterOutValueCurrentMonth;
    private double waterOutSumm;

    private double rentSum;

    private int homemates;

    private Date paymentDate;

}
