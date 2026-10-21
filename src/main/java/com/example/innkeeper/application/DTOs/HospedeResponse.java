package com.example.innkeeper.application.DTOs;

public record HospedeResponse(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone
) {
}