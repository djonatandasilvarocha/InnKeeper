package com.example.innkeeper.application.service;

import com.example.innkeeper.application.DTOs.*;
import com.example.innkeeper.domain.entities.EnumStatusQuarto;
import com.example.innkeeper.domain.entities.EnumStatusReserva;
import com.example.innkeeper.domain.entities.Hospede;
import com.example.innkeeper.domain.entities.Quarto;
import com.example.innkeeper.domain.entities.Reserva;
import com.example.innkeeper.domain.repository.HospedeRepository;
import com.example.innkeeper.domain.repository.QuartoRepository;
import com.example.innkeeper.domain.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final HospedeRepository hospedeRepository;
    private final QuartoRepository quartoRepository;

    public ReservaService(ReservaRepository reservaRepository, HospedeRepository hospedeRepository, QuartoRepository quartoRepository) {
        this.reservaRepository = reservaRepository;
        this.hospedeRepository = hospedeRepository;
        this.quartoRepository = quartoRepository;
    }

    // --- MÉTODOS DE LEITURA (Retornam DTOs) ---

    public List<ReservaResponse> listarTodasDto() {
        return reservaRepository.findAll()
                .stream()
                .map(ReservaResponse::new)
                .toList();
    }

    public ReservaResponse buscarPorIdDto(Long id) {
        return reservaRepository.findById(id)
                .map(ReservaResponse::new)
                .orElse(null);
    }

    public boolean existePorId(Long id) {
        return reservaRepository.existsById(id);
    }

    // --- MÉTODOS DE ESCRITA E ATUALIZAÇÃO (Recebem DTOs e retornam Response DTO) ---

    public ReservaResponse criar(CriarReservaRequest request) {
        Hospede hospede = hospedeRepository.findById(request.hospedeId())
                .orElseThrow(() -> new RuntimeException("Hóspede não encontrado com ID: " + request.hospedeId()));

        Quarto quarto = quartoRepository.findById(request.quartoId())
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado com ID: " + request.quartoId()));

        Reserva reserva = new Reserva();
        reserva.setDataEntrada(request.dataEntrada());
        reserva.setDataSaida(request.dataSaida());
        reserva.setValorTotal(request.valorTotal());
        reserva.setStatus(EnumStatusReserva.PENDENTE); // Status inicial padrão para nova reserva
        reserva.setHospede(hospede);
        reserva.setQuarto(quarto);

        Reserva salva = reservaRepository.save(reserva);
        return new ReservaResponse(salva);
    }

    public ReservaResponse atualizarDto(Long id, AtualizarReservaRequest request) {
        Optional<Reserva> reservaBanco = reservaRepository.findById(id);
        if (reservaBanco.isPresent()) {
            Reserva reserva = reservaBanco.get();

            reserva.setDataEntrada(request.dataEntrada());
            reserva.setDataSaida(request.dataSaida());
            reserva.setValorTotal(request.valorTotal());

            if (request.status() != null) {
                reserva.setStatus(request.status());
            }

            if (request.hospedeId() != null) {
                Hospede hospede = hospedeRepository.findById(request.hospedeId())
                        .orElseThrow(() -> new RuntimeException("Hóspede não encontrado"));
                reserva.setHospede(hospede);
            }

            if (request.quartoId() != null) {
                Quarto quarto = quartoRepository.findById(request.quartoId())
                        .orElseThrow(() -> new RuntimeException("Quarto não encontrado"));
                reserva.setQuarto(quarto);
            }

            Reserva atualizada = reservaRepository.save(reserva);
            return new ReservaResponse(atualizada);
        }
        return null;
    }

    public ReservaResponse atualizarParcialDto(Long id, AtualizarReservaRequest request) {
        Optional<Reserva> reservaBanco = reservaRepository.findById(id);
        if (reservaBanco.isPresent()) {
            Reserva reserva = reservaBanco.get();

            if (request.dataEntrada() != null) {
                reserva.setDataEntrada(request.dataEntrada());
            }
            if (request.dataSaida() != null) {
                reserva.setDataSaida(request.dataSaida());
            }
            if (request.valorTotal() != null) {
                reserva.setValorTotal(request.valorTotal());
            }
            if (request.status() != null) {
                reserva.setStatus(request.status());
            }
            if (request.hospedeId() != null) {
                Hospede hospede = hospedeRepository.findById(request.hospedeId())
                        .orElseThrow(() -> new RuntimeException("Hóspede não encontrado"));
                reserva.setHospede(hospede);
            }
            if (request.quartoId() != null) {
                Quarto quarto = quartoRepository.findById(request.quartoId())
                        .orElseThrow(() -> new RuntimeException("Quarto não encontrado"));
                reserva.setQuarto(quarto);
            }

            Reserva atualizada = reservaRepository.save(reserva);
            return new ReservaResponse(atualizada);
        }
        return null;
    }

    // --- CHECKOUT E REGRAS DE NEGÓCIO ---

    public ReservaResponse realizarCheckoutDto(Long id) {
        Optional<Reserva> reservaBanco = reservaRepository.findById(id);

        if (reservaBanco.isEmpty()) {
            return null;
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

        Reserva salva = reservaRepository.save(reserva);
        return new ReservaResponse(salva);
    }

    public boolean deletarLogicoOuFisico(Long id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}