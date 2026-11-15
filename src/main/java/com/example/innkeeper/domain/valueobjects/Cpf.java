package com.example.innkeeper.domain.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Cpf(String value) {
    public Cpf {
        if (value == null || value.replaceAll("\\D", "").length() != 11) {
            throw new IllegalArgumentException("CPF inválido.");
        }
        value = value.replaceAll("\\D", ""); // Armazena apenas dígitos
    }
}
