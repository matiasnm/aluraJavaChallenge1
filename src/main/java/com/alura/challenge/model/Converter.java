package com.alura.challenge.model;

import java.util.HashMap;

public class Converter {

    private HashMap<String, Double> rates;
    
    public Converter(HashMap<String, Double> rates) {
        this.rates = rates;
    }

    public Double getRate(String code) {
        return rates.get(code);
    }

    public Double convert(Double quantity, String from, String to) {
        return quantity / rates.get(from) * rates.get(to);
    }

}