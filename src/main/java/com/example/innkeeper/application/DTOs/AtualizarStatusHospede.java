package com.example.innkeeper.application.DTOs;

import com.example.innkeeper.domain.entities.EnumStatusHospede;

public record AtualizarStatusHospede(
        String nome,
        String cpf,
        String telefone,
        String email,
        EnumStatusHospede status
) {
}
