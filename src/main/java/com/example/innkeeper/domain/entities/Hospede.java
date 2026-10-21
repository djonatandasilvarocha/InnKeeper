package com.example.innkeeper.domain.entities;

import com.example.innkeeper.domain.valueobjects.Celular;
import com.example.innkeeper.domain.valueobjects.Cpf;
import com.example.innkeeper.domain.valueobjects.Email;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hospede")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hospede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "cpf", nullable = false, unique = true))
    private Cpf cpf;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "telefone"))
    private Celular telefone;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false, unique = true))
    private Email email;

    @Enumerated(EnumType.STRING)
    private EnumStatusHospede status;
}