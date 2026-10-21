package com.example.innkeeper.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "quarto") // Nome exato da sua tabela no PostgreSQL
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    private String tipo;

    private Integer capacidade;

    private BigDecimal diaria;

    @Enumerated(EnumType.STRING)
    private EnumStatusQuarto status;
}