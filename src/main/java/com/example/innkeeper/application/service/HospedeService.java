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

    // --- MÉTODOS DE LEITURA ---

    public List<HospedeResponse> listarTodosDto() {
        return hospedeRepository.findAll().stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    public HospedeResponse buscarPorIdDto(Long id) {
        return hospedeRepository.findById(id)
                .map(this::converterParaResponse)
                .orElse(null);
    }

    public boolean existePorId(Long id) {
        return hospedeRepository.existsById(id);
    }

    // --- MÉTODOS DE CRIAÇÃO E ATUALIZAÇÃO ---

    public HospedeResponse criar(CriarHospedeRequest request) {
        Hospede hospede = new Hospede();
        hospede.setNome(request.nome());

        if (request.cpf() != null) {
            hospede.setCpf(new Cpf(request.cpf()));
        }
        if (request.email() != null) {
            hospede.setEmail(new Email(request.email()));
        }
        if (request.telefone() != null) {
            hospede.setTelefone(new Celular(request.telefone())); // Ou Telefone, conforme sua classe
        }

        hospede.setStatus(EnumStatusHospede.ATIVO);

        Hospede salvo = hospedeRepository.save(hospede);
        return converterParaResponse(salvo);
    }

    public HospedeResponse atualizarDto(Long id, AtualizarHospedeRequest request) {
        Optional<Hospede> hospedeBanco = hospedeRepository.findById(id);
        if (hospedeBanco.isPresent()) {
            Hospede hospede = hospedeBanco.get();

            hospede.setNome(request.nome());

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

    public HospedeResponse atualizarParcialDto(Long id, AtualizarHospedeRequest request) {
        Optional<Hospede> hospedeBanco = hospedeRepository.findById(id);
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

    public boolean deletarPorId(Long id) {
        if (hospedeRepository.existsById(id)) {
            hospedeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // --- MAPPING PRIVADO DE ENTIDADE PARA DTO ---

    private HospedeResponse converterParaResponse(Hospede hospede) {
        return new HospedeResponse(
                hospede.getId(),
                hospede.getNome() != null ? hospede.getNome() : "",
                hospede.getCpf() != null ? hospede.getCpf().value() : "",
                hospede.getEmail() != null ? hospede.getEmail().value() : "",
                hospede.getTelefone() != null ? hospede.getTelefone().value() : ""
        );
    }
}