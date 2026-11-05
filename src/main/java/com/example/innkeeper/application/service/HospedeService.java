
package com.example.innkeeper.application.service;

import com.example.innkeeper.application.DTOs.AtualizarHospedeRequest;
import com.example.innkeeper.application.DTOs.CriarHospedeRequest;
import com.example.innkeeper.application.DTOs.HospedeResponse;
import com.example.innkeeper.domain.entities.EnumStatusHospede;
import com.example.innkeeper.domain.entities.Hospede;
import com.example.innkeeper.domain.repository.HospedeRepository;
import com.example.innkeeper.domain.valueobjects.Celular;
import com.example.innkeeper.domain.valueobjects.Cpf;
import com.example.innkeeper.domain.valueobjects.Email;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HospedeService {

    private final HospedeRepository hospedeRepository;

    public HospedeService(HospedeRepository hospedeRepository) {
        this.hospedeRepository = hospedeRepository;
    }

    // MÉTODOS DE LEITURA

    public List<HospedeResponse> listarTodosDto() {
        return hospedeRepository.findByStatusNot(EnumStatusHospede.EXCLUIDO)
                .stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    public HospedeResponse buscarPorIdDto(Long id) {
        return hospedeRepository
                .findByIdAndStatusNot(id, EnumStatusHospede.EXCLUIDO)
                .map(this::converterParaResponse)
                .orElse(null);
    }

    public boolean existePorId(Long id) {
        return hospedeRepository
                .findByIdAndStatusNot(id, EnumStatusHospede.EXCLUIDO)
                .isPresent();
    }

    // MÉTODO DE CRIAÇÃO

    public HospedeResponse criar(CriarHospedeRequest request) {

        if (request.cpf() == null || request.email() == null) {
            throw new IllegalArgumentException(
                    "CPF e e-mail são obrigatórios."
            );
        }

        Hospede hospede = new Hospede();

        hospede.setNome(request.nome());
        hospede.setCpf(new Cpf(request.cpf()));
        hospede.setEmail(new Email(request.email()));

        if (request.telefone() != null) {
            hospede.setTelefone(new Celular(request.telefone()));
        }

        hospede.setStatus(EnumStatusHospede.ATIVO);

        Hospede salvo = hospedeRepository.save(hospede);

        return converterParaResponse(salvo);
    }

    // MÉTODO DE ATUALIZAÇÃO COMPLETA

    public HospedeResponse atualizarDto(
            Long id,
            AtualizarHospedeRequest request
    ) {

        Optional<Hospede> hospedeBanco =
                hospedeRepository.findByIdAndStatusNot(
                        id,
                        EnumStatusHospede.EXCLUIDO
                );

        if (hospedeBanco.isPresent()) {

            Hospede hospede = hospedeBanco.get();

            if (request.nome() == null ||
                    request.cpf() == null ||
                    request.email() == null) {
                throw new IllegalArgumentException(
                        "Nome, CPF e e-mail são obrigatórios."
                );
            }

            hospede.setNome(request.nome());
            hospede.setCpf(new Cpf(request.cpf()));
            hospede.setEmail(new Email(request.email()));

            if (request.telefone() != null) {
                hospede.setTelefone(new Celular(request.telefone()));
            }

            if (request.status() != null) {
                hospede.setStatus(request.status());
            }

            Hospede atualizado = hospedeRepository.save(hospede);

            return converterParaResponse(atualizado);
        }

        return null;
    }

    // MÉTODO DE ATUALIZAÇÃO PARCIAL

    public HospedeResponse atualizarParcialDto(
            Long id,
            AtualizarHospedeRequest request
    ) {

        Optional<Hospede> hospedeBanco =
                hospedeRepository.findByIdAndStatusNot(
                        id,
                        EnumStatusHospede.EXCLUIDO
                );

        if (hospedeBanco.isPresent()) {

            Hospede hospede = hospedeBanco.get();

            if (request.nome() != null) {
                hospede.setNome(request.nome());
            }

            if (request.cpf() != null) {
                hospede.setCpf(new Cpf(request.cpf()));
            }

            if (request.email() != null) {
                hospede.setEmail(new Email(request.email()));
            }

            if (request.telefone() != null) {
                hospede.setTelefone(new Celular(request.telefone()));
            }

            if (request.status() != null) {
                hospede.setStatus(request.status());
            }

            Hospede atualizado = hospedeRepository.save(hospede);

            return converterParaResponse(atualizado);
        }

        return null;
    }

    // MÉTODO DE EXCLUSÃO LÓGICA

    public boolean deletarPorId(Long id) {

        Optional<Hospede> hospedeBanco =
                hospedeRepository.findByIdAndStatusNot(
                        id,
                        EnumStatusHospede.EXCLUIDO
                );

        if (hospedeBanco.isPresent()) {

            Hospede hospede = hospedeBanco.get();

            hospede.setStatus(EnumStatusHospede.EXCLUIDO);

            hospedeRepository.save(hospede);

            return true;
        }

        return false;
    }

    // CONVERSÃO DE ENTIDADE PARA DTO

    private HospedeResponse converterParaResponse(Hospede hospede) {

        return new HospedeResponse(
                hospede.getId(),
                hospede.getNome() != null ? hospede.getNome() : "",
                hospede.getCpf() != null ? hospede.getCpf().value() : "",
                hospede.getEmail() != null ? hospede.getEmail().value() : "",
                hospede.getTelefone() != null ? hospede.getTelefone().value() : "",
                hospede.getStatus()
        );
    }
}
