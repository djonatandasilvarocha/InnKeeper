package com.example.innkeeper.application.DTOs;

public record AtualizarUsuarioRequest(
        String nome,
        String cpf,
        String email,
        String senha
) {
}