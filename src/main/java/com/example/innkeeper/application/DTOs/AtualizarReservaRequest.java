package com.example.innkeeper.application.DTOs;

import com.example.innkeeper.domain.entities.EnumStatusReserva;
import java.math.BigDecimal;
import java.time.LocalDate;

public record AtualizarReservaRequest(
        LocalDate dataEntrada,
        LocalDate dataSaida,
        BigDecimal valorTotal,
        EnumStatusReserva status, // Adicione esta linha aqui
        Long hospedeId,
        Long quartoId
) {
}