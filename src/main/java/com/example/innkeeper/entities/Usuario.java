package com.example.innkeeper.entities;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    public Long id;

    public String nome;

    public String cpf;

    public String senha;

    public String email;

}
