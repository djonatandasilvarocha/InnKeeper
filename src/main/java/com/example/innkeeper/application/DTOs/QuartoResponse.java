package com.example.innkeeper.application.DTOs;

import com.example.innkeeper.domain.entities.EnumStatusQuarto;
import com.example.innkeeper.domain.entities.Quarto;
import java.math.BigDecimal;

public record QuartoResponse(
        Long id,
        String numero, // Alterado de Integer para String
        String tipo,
        Integer capacidade,
        BigDecimal diaria,
        EnumStatusQuarto status
) {
    public QuartoResponse(Quarto quarto) {
        this(
                quarto.getId(),
                quarto.getNumero(),
                quarto.getTipo(),
                quarto.getCapacidade(),
                quarto.getDiaria(),
                quarto.getStatus()
        );
    }
}