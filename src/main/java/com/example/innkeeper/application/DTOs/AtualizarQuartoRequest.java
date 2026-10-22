package com.example.innkeeper.application.DTOs;

import com.example.innkeeper.domain.entities.EnumStatusQuarto;
import java.math.BigDecimal;

public record AtualizarQuartoRequest(
        String numero, // Alterado de Integer para String
        String tipo,
        Integer capacidade,
        BigDecimal diaria,
        EnumStatusQuarto status
) {
}