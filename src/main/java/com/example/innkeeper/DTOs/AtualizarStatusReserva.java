package com.example.innkeeper.DTOs;

import com.example.innkeeper.entities.EnumStatusReserva;

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
