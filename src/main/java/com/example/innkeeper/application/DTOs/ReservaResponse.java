package com.example.innkeeper.application.DTOs;

import com.example.innkeeper.domain.entities.Reserva;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservaResponse(
        Long id,
        LocalDate dataEntrada,
        LocalDate dataSaida,
        BigDecimal valorTotal,
        String status,
        Long hospedeId,
        Long quartoId
) {
    public ReservaResponse(Reserva reserva) {
        this(
                reserva.getId(),
                reserva.getDataEntrada(),
                reserva.getDataSaida(),
                reserva.getValorTotal(),
                reserva.getStatus() != null ? reserva.getStatus().name() : null,
                reserva.getHospede() != null ? reserva.getHospede().getId() : null,
                reserva.getQuarto() != null ? reserva.getQuarto().getId() : null
        );
    }
}