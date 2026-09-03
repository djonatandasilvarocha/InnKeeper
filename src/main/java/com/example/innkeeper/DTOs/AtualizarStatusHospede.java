package com.example.innkeeper.DTOs;

import com.example.innkeeper.entities.EnumStatusHospede;

public record AtualizarStatusHospede(
        String nome,
        String cpf,
        String telefone,
        String email,
        EnumStatusHospede status
) {
}
