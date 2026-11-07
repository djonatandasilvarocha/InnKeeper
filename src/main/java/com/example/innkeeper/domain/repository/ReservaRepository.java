package com.example.innkeeper.domain.repository;

import com.example.innkeeper.domain.entities.EnumStatusReserva;
import com.example.innkeeper.domain.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Buscar reservas de um usuário específico
    List<Reserva> findByUsuario_IdAndStatusNot(
            Long usuarioId,
            EnumStatusReserva status
    );

    // Buscar uma reserva específica pertencente a um usuário
    Optional<Reserva> findByIdAndUsuario_IdAndStatusNot(
            Long id,
            Long usuarioId,
            EnumStatusReserva status
    );

    // Verificar conflito ao criar uma reserva
    boolean existsByQuarto_IdAndStatusInAndDataEntradaLessThanAndDataSaidaGreaterThan(
            Long quartoId,
            List<EnumStatusReserva> status,
            LocalDate dataSaida,
            LocalDate dataEntrada
    );

    // Verificar conflito ao atualizar uma reserva
    boolean existsByQuarto_IdAndIdNotAndStatusInAndDataEntradaLessThanAndDataSaidaGreaterThan(
            Long quartoId,
            Long reservaId,
            List<EnumStatusReserva> status,
            LocalDate dataSaida,
            LocalDate dataEntrada
    );

    // Verificar outra reserva em andamento durante o checkout
    boolean existsByQuarto_IdAndIdNotAndStatusInAndDataEntradaLessThanEqualAndDataSaidaGreaterThan(
            Long quartoId,
            Long reservaId,
            List<EnumStatusReserva> status,
            LocalDate dataAtualEntrada,
            LocalDate dataAtualSaida
    );
}