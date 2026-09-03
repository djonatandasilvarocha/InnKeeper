package com.example.innkeeper.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String numero;

    public String tipo;

    public Integer capacidade;

    public BigDecimal diaria;

    @Enumerated(EnumType.STRING)
    public EnumStatusQuarto status;
}
