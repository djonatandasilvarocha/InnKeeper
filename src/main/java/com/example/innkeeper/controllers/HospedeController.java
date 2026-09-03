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
@RequestMapping("/hospedes")
@Tag(
        name = "Hospedes",
        description = "Operações relacionadas ao gerenciamento de hóspedes."
)
public class HospedeController {

    @Autowired
    private HospedeRepository hospedeRepository;

    @Operation(
            summary = "Listar hóspedes",
            description = "Retorna todos os hóspedes cadastrados no sistema."
    )
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(hospedeRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Hospede> criar(@RequestBody Hospede hospede) {
        var hospedeBanco = hospedeRepository.save(hospede);
        return ResponseEntity.ok(hospedeBanco);
    }

    // PUT - Atualizar hóspede completo
    @PutMapping("/{id}")
    public ResponseEntity<Hospede> atualizar(
            @PathVariable Long id,
            @RequestBody Hospede hospede) {

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
    }

    // PATCH - Atualizar hóspede parcialmente
    @PatchMapping("/{id}")
    public ResponseEntity<Hospede> atualizarParcial(
            @PathVariable Long id,
            @RequestBody Hospede hospede) {

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

    // DELETE - Excluir hóspede
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        if (!hospedeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        hospedeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}