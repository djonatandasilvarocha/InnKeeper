package com.example.innkeeper.DTOs;

import com.example.innkeeper.entities.EnumStatusQuarto;

import java.math.BigDecimal;

public record AtualizarStatusQuarto(
        String numero,
        String tipo,
        Integer capacidade,
        BigDecimal diaria,
        EnumStatusQuarto status
) {
}
