package com.example.innkeeper.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hospede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String nome;

    public String cpf;

    public String telefone;

    public String email;

    @Enumerated(EnumType.STRING)
    public EnumStatusHospede status;
}
