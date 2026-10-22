package com.example.innkeeper.application.DTOs;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CriarReservaRequest(
        LocalDate dataEntrada,
        LocalDate dataSaida,
        BigDecimal valorTotal,
        Long hospedeId,
        Long quartoId
) {
}