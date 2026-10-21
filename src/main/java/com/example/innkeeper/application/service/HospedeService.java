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

    // Método necessário para retornar a Entidade pura nas operações de atualização
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

    // Método auxiliar para converter a Entidade de Domínio na DTO de Resposta
    private HospedeResponse converterParaResponse(Hospede hospede) {
        return new HospedeResponse(
                hospede.getId(),
                hospede.getNome() != null ? hospede.getNome().toString() : "",
                hospede.getCpf() != null ? hospede.getCpf().toString() : "",
                hospede.getEmail() != null ? hospede.getEmail().toString() : "",
                hospede.getTelefone() != null ? hospede.getTelefone().toString() : ""
        );
    }
}