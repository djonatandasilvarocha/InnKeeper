package com.example.innkeeper.application.service;

import com.example.innkeeper.application.DTOs.AtualizarQuartoRequest;
import com.example.innkeeper.application.DTOs.CriarQuartoRequest;
import com.example.innkeeper.application.DTOs.QuartoResponse;
import com.example.innkeeper.domain.entities.EnumStatusQuarto;
import com.example.innkeeper.domain.entities.Quarto;
import com.example.innkeeper.domain.repository.QuartoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuartoService {

    private final QuartoRepository quartoRepository;

    public QuartoService(QuartoRepository quartoRepository) {
        this.quartoRepository = quartoRepository;
    }

    // --- MÉTODOS DE LEITURA (Retornam DTOs) ---

    public List<QuartoResponse> listarTodosDto() {
        return quartoRepository.findAll()
                .stream()
                .map(QuartoResponse::new)
                .toList();
    }

    public QuartoResponse buscarPorIdDto(Long id) {
        return quartoRepository.findById(id)
                .map(QuartoResponse::new)
                .orElse(null);
    }

    public boolean existePorId(Long id) {
        return quartoRepository.existsById(id);
    }

    // --- MÉTODOS DE ESCRITA E ATUALIZAÇÃO (Recebem DTOs e retornam Response DTO) ---

    public QuartoResponse criar(CriarQuartoRequest request) {
        Quarto quarto = new Quarto();
        quarto.setNumero(request.numero());
        quarto.setTipo(request.tipo());
        quarto.setCapacidade(request.capacidade());
        quarto.setDiaria(request.diaria());
        quarto.setStatus(EnumStatusQuarto.DISPONIVEL); // Status inicial padrão

        Quarto salvo = quartoRepository.save(quarto);
        return new QuartoResponse(salvo);
    }

    public QuartoResponse atualizarDto(Long id, AtualizarQuartoRequest request) {
        Optional<Quarto> quartoBanco = quartoRepository.findById(id);
        if (quartoBanco.isPresent()) {
            Quarto quarto = quartoBanco.get();

            quarto.setNumero(request.numero());
            quarto.setTipo(request.tipo());
            quarto.setCapacidade(request.capacidade());
            quarto.setDiaria(request.diaria());
            if (request.status() != null) {
                quarto.setStatus(request.status());
            }

            Quarto atualizado = quartoRepository.save(quarto);
            return new QuartoResponse(atualizado);
        }
        return null;
    }

    public QuartoResponse atualizarParcialDto(Long id, AtualizarQuartoRequest request) {
        Optional<Quarto> quartoBanco = quartoRepository.findById(id);
        if (quartoBanco.isPresent()) {
            Quarto quarto = quartoBanco.get();

            if (request.numero() != null) {
                quarto.setNumero(request.numero());
            }
            if (request.tipo() != null) {
                quarto.setTipo(request.tipo());
            }
            if (request.capacidade() != null) {
                quarto.setCapacidade(request.capacidade());
            }
            if (request.diaria() != null) {
                quarto.setDiaria(request.diaria());
            }
            if (request.status() != null) {
                quarto.setStatus(request.status());
            }

            Quarto atualizado = quartoRepository.save(quarto);
            return new QuartoResponse(atualizado);
        }
        return null;
    }

    public boolean deletarPorId(Long id) {
        if (quartoRepository.existsById(id)) {
            quartoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}