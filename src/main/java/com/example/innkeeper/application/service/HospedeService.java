package com.example.innkeeper.application.service;

import com.example.innkeeper.domain.entities.Hospede;
import com.example.innkeeper.domain.repository.HospedeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospedeService {

    private final HospedeRepository hospedeRepository;

    public HospedeService(HospedeRepository hospedeRepository) {
        this.hospedeRepository = hospedeRepository;
    }

    public List<Hospede> listarTodos() {
        return hospedeRepository.findAll();
    }

    public Optional<Hospede> buscarPorId(Long id) {
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
}