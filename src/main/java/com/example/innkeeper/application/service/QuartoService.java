
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

    // MÉTODOS DE LEITURA

    public List<QuartoResponse> listarTodosDto() {
        return quartoRepository
                .findByStatusNot(EnumStatusQuarto.EXCLUIDO)
                .stream()
                .map(QuartoResponse::new)
                .toList();
    }

    public QuartoResponse buscarPorIdDto(Long id) {
        return quartoRepository
                .findByIdAndStatusNot(
                        id,
                        EnumStatusQuarto.EXCLUIDO
                )
                .map(QuartoResponse::new)
                .orElse(null);
    }

    public boolean existePorId(Long id) {
        return quartoRepository
                .findByIdAndStatusNot(
                        id,
                        EnumStatusQuarto.EXCLUIDO
                )
                .isPresent();
    }

    // MÉTODO DE CRIAÇÃO

    public QuartoResponse criar(CriarQuartoRequest request) {

        Quarto quarto = new Quarto();

        quarto.setNumero(request.numero());
        quarto.setTipo(request.tipo());
        quarto.setCapacidade(request.capacidade());
        quarto.setDiaria(request.diaria());

        quarto.setStatus(EnumStatusQuarto.DISPONIVEL);

        Quarto salvo = quartoRepository.save(quarto);

        return new QuartoResponse(salvo);
    }

    // MÉTODO DE ATUALIZAÇÃO COMPLETA

    public QuartoResponse atualizarDto(
            Long id,
            AtualizarQuartoRequest request
    ) {

        Optional<Quarto> quartoBanco =
                quartoRepository.findByIdAndStatusNot(
                        id,
                        EnumStatusQuarto.EXCLUIDO
                );

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

    // MÉTODO DE ATUALIZAÇÃO PARCIAL

    public QuartoResponse atualizarParcialDto(
            Long id,
            AtualizarQuartoRequest request
    ) {

        Optional<Quarto> quartoBanco =
                quartoRepository.findByIdAndStatusNot(
                        id,
                        EnumStatusQuarto.EXCLUIDO
                );

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

    // MÉTODO DE EXCLUSÃO LÓGICA

    public boolean deletarPorId(Long id) {

        Optional<Quarto> quartoBanco =
                quartoRepository.findByIdAndStatusNot(
                        id,
                        EnumStatusQuarto.EXCLUIDO
                );

        if (quartoBanco.isPresent()) {

            Quarto quarto = quartoBanco.get();

            quarto.setStatus(EnumStatusQuarto.EXCLUIDO);

            quartoRepository.save(quarto);

            return true;
        }

        return false;
    }
}
