package com.example.innkeeper.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public Long id;

    public LocalDate dataEntrada;

    public LocalDate dataSaida;

    public BigDecimal valorTotal;

    public String status;

    @ManyToOne
    @JoinColumn(name = "hospede_id")
    private Hospede hospede;

    @ManyToOne
    @JoinColumn(name = "quarto_id")
    private Quarto quarto;
}
