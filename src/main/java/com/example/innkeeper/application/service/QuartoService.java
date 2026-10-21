package com.example.innkeeper.application.service;

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

    public List<Quarto> listarTodos() {
        return quartoRepository.findAll();
    }

    public Optional<Quarto> buscarPorId(Long id) {
        return quartoRepository.findById(id);
    }

    public Quarto salvar(Quarto quarto) {
        return quartoRepository.save(quarto);
    }

    public boolean existePorId(Long id) {
        return quartoRepository.existsById(id);
    }

    public void deletar(Long id) {
        quartoRepository.deleteById(id);
    }
}