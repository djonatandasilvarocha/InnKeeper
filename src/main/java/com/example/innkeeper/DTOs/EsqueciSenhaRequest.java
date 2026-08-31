package com.example.innkeeper.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EsqueciSenhaRequest(@NotBlank(message = "O email é obrigatório")
                                  @Email(message = "Informe um email válido") String email) {

}
