package com.alura.challenge.dto;

import java.util.HashMap;

public record ratesRequestDto(
    String result,
    String base_code,
    HashMap<String, Double> conversion_rates) {
}