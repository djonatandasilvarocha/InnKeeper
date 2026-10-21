package com.example.innkeeper.domain.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
public record Email(String value) {
    private static final Pattern PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public Email {
        if (value == null || !PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("E-mail em formato inválido.");
        }
    }
}