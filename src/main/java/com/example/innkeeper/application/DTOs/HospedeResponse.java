package com.example.innkeeper.application.DTOs;

import com.example.innkeeper.domain.entities.EnumStatusHospede;

public record HospedeResponse(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone,
        EnumStatusHospede status
) {
}