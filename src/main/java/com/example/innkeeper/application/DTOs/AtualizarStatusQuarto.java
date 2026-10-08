package com.example.innkeeper.application.DTOs;

import com.example.innkeeper.domain.entities.EnumStatusQuarto;

import java.math.BigDecimal;

public record AtualizarStatusQuarto(
        String numero,
        String tipo,
        Integer capacidade,
        BigDecimal diaria,
        EnumStatusQuarto status
) {
}
