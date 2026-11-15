package com.example.innkeeper.application.DTOs;

import java.math.BigDecimal;

public record CriarQuartoRequest(
        String numero, // Alterado de Integer para String
        String tipo,
        Integer capacidade,
        BigDecimal diaria
) {
}