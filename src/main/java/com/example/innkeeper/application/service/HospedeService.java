package com.example.innkeeper.application.service;

import com.example.innkeeper.application.DTOs.HospedeResponse;
import com.example.innkeeper.domain.entities.Hospede;
import com.example.innkeeper.domain.repository.HospedeRepository;
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

    public List<HospedeResponse> listarTodos() {
        return hospedeRepository.findAll().stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    public Optional<HospedeResponse> buscarPorId(Long id) {
        return hospedeRepository.findById(id)
                .map(this::converterParaResponse);
    }

    // Metodo necessário para retornar a Entidade pura nas operações de atualização da Controller
    public Optional<Hospede> buscarEntidadePorId(Long id) {
        return hospedeRepository.findById(id);
    }

    public Hospede salvar(Hospede hospede) {
        return hospedeRepository.save(hospede);
    }

    public boolean existePorId(Long id) {
        return hospedeRepository.existsById(id);
    }

    public void deletar(Long id) {
        hospedeRepository.deleteById(id);
    }

    private HospedeResponse converterParaResponse(Hospede hospede) {
        return new HospedeResponse(
                hospede.getId(),
                hospede.getNome() != null ? hospede.getNome().toString() : "", // O nome já é texto puro ou String
                hospede.getCpf() != null ? hospede.getCpf().value() : "",       // Record Cpf
                hospede.getEmail() != null ? hospede.getEmail().value() : "",   // Record Email
                hospede.getTelefone() != null ? hospede.getTelefone().value() : "" // Record Telefone/Celular
        );
    }
}