package com.example.innkeeper.application.DTOs;

public record CriarHospedeRequest(
        String nome,
        String cpf,
        String telefone,
        String email
) {
}