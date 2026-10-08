package com.example.innkeeper.application.DTOs;

import com.example.innkeeper.domain.entities.EnumStatusReserva;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AtualizarStatusReserva(
        LocalDate dataEntrada,
        LocalDate dataSaida,
        BigDecimal valorTotal,
        EnumStatusReserva status,
        Long hospedeId,
        Long quartoId
) {
}
