package com.example.innkeeper.domain.repository;

import com.example.innkeeper.domain.entities.Quarto;
import com.example.innkeeper.domain.entities.EnumStatusQuarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {

    List<Quarto> findByStatusNot(EnumStatusQuarto status);

    Optional<Quarto> findByIdAndStatusNot(
            Long id,
            EnumStatusQuarto status
    );
}