package com.example.innkeeper.controllers;

import com.example.innkeeper.entities.Hospede;
import com.example.innkeeper.repository.HospedeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hospede")
@Tag(
        name = "Hospedes",
        description = "Operações relacionadas ao gerenciamento de hóspedes."
)
public class HospedeController {

    @Autowired
    private HospedeRepository hospedeRepository;

    @GetMapping
    @Operation(
            summary = "Listar hóspedes",
            description = "Retorna todos os hóspedes cadastrados no sistema."
    )
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(hospedeRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar hóspede por id",
            description = "Retorna um hóspede específico através do seu id."
    )
    public ResponseEntity<Hospede> buscarPorId(@PathVariable Long id) {
        Hospede hospedeBanco = hospedeRepository.findById(id).orElse(null);
        if (hospedeBanco != null) {
            return ResponseEntity.ok(hospedeBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Criar hóspede",
            description = "Efetua a criação de um novo hóspede."
    )
    public ResponseEntity<Hospede> criar(@RequestBody Hospede hospede) {
        var hospedeBanco = hospedeRepository.save(hospede);
        return ResponseEntity.ok(hospedeBanco);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar hóspede",
            description = "Atualiza todos os dados de um hóspede existente."
    )
    public ResponseEntity<Hospede> atualizar(@PathVariable Long id, @RequestBody Hospede hospede) {

        try {
            var hospedeBanco = hospedeRepository.findById(id);

            if (hospedeBanco.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Hospede hospedeAtual = hospedeBanco.get();

            hospedeAtual.nome = hospede.nome;
            hospedeAtual.cpf = hospede.cpf;
            hospedeAtual.telefone = hospede.telefone;
            hospedeAtual.email = hospede.email;

            return ResponseEntity.ok(hospedeRepository.save(hospedeAtual));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar hóspede parcialmente",
            description = "Atualiza apenas os campos informados de um hóspede existente."
    )
    public ResponseEntity<Hospede> atualizarParcial(@PathVariable Long id, @RequestBody Hospede hospede) {

        var hospedeBanco = hospedeRepository.findById(id);

        if (hospedeBanco.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Hospede hospedeAtual = hospedeBanco.get();

        if (hospede.nome != null) {
            hospedeAtual.nome = hospede.nome;
        }

        if (hospede.cpf != null) {
            hospedeAtual.cpf = hospede.cpf;
        }

        if (hospede.telefone != null) {
            hospedeAtual.telefone = hospede.telefone;
        }

        if (hospede.email != null) {
            hospedeAtual.email = hospede.email;
        }

        return ResponseEntity.ok(hospedeRepository.save(hospedeAtual));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir hóspede",
            description = "Efetua a exclusão de um hóspede do sistema."
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        if (!hospedeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        hospedeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}