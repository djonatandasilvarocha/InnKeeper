package com.example.innkeeper.application.DTOs;

public record CriarUsuarioRequest(
        String nome,
        String cpf,
        String email,
        String senha
) {
}