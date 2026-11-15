package com.example.innkeeper.domain.repository;

import com.example.innkeeper.domain.entities.EnumStatusQuarto;
import com.example.innkeeper.domain.entities.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {

    List<Quarto> findByUsuario_IdAndStatusNot(
            Long usuarioId,
            EnumStatusQuarto status
    );

    Optional<Quarto> findByIdAndUsuario_IdAndStatusNot(
            Long id,
            Long usuarioId,
            EnumStatusQuarto status
    );
}