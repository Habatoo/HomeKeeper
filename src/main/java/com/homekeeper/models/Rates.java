package com.homekeeper.models;

import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class Rates {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String indexName;
    private double indexRate;

}
