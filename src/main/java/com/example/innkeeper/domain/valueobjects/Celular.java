package com.example.innkeeper.domain.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Celular(String value) {
    public Celular {
        if (value == null || !value.replaceAll("\\D", "").matches("^\\d{10,11}$")) {
            throw new IllegalArgumentException("Número de celular inválido.");
        }
        value = value.replaceAll("\\D", "");
    }
}
