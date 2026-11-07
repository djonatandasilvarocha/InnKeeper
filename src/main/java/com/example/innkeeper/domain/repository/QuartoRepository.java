package com.example.innkeeper.domain.repository;

import com.example.innkeeper.domain.entities.EnumStatusQuarto;
import com.example.innkeeper.domain.entities.Quarto;

import java.util.List;
import java.util.Optional;

public interface QuartoRepository {

    List<Quarto> findByUsuario_IdAndStatusNot(
            Long usuarioId,
            EnumStatusQuarto status
    );

    Optional<Quarto> findByIdAndUsuario_IdAndStatusNot(
            Long id,
            Long usuarioId,
            EnumStatusQuarto status
    );

    Quarto save(Quarto quarto);
}