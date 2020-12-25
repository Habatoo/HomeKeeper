package com.homekeeper.models;

import org.springframework.data.annotation.Id;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class Tariffs {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private ETariffValues tariffName;

    private double tariffValue;

    private Date tariffDate;

}
