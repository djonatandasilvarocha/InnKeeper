package com.example.innkeeper.application.service;

import com.example.innkeeper.domain.entities.EnumStatusQuarto;
import com.example.innkeeper.domain.entities.EnumStatusReserva;
import com.example.innkeeper.domain.entities.Reserva;
import com.example.innkeeper.domain.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findById(id);
    }

    public Reserva salvar(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public boolean existePorId(Long id) {
        return reservaRepository.existsById(id);
    }

    public void deletar(Long id) {
        reservaRepository.deleteById(id);
    }

    // Regra de negócio isolada no Service!
    public Optional<Reserva> realizarCheckout(Long id) {
        Optional<Reserva> reservaBanco = reservaRepository.findById(id);

        if (reservaBanco.isEmpty()) {
            return Optional.empty();
        }

        Reserva reserva = reservaBanco.get();

        if (reserva.getStatus() == EnumStatusReserva.FINALIZADA || reserva.getStatus() == EnumStatusReserva.CANCELADA) {
            throw new IllegalArgumentException("Reserva já finalizada ou cancelada.");
        }

        long dias = ChronoUnit.DAYS.between(reserva.getDataEntrada(), reserva.getDataSaida());
        BigDecimal diaria = reserva.getQuarto().getDiaria();

        reserva.setValorTotal(diaria.multiply(BigDecimal.valueOf(dias)));
        reserva.setStatus(EnumStatusReserva.FINALIZADA);

        if (reserva.getQuarto() != null) {
            reserva.getQuarto().setStatus(EnumStatusQuarto.DISPONIVEL);
        }

        return Optional.of(reservaRepository.save(reserva));
    }
}