package com.example.innkeeper.application.DTOs;

import com.example.innkeeper.domain.entities.EnumStatusHospede;

public record AtualizarHospedeRequest(
        String nome,
        String cpf,
        String telefone,
        String email,
        EnumStatusHospede status
) {
}