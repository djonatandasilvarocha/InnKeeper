package com.example.innkeeper.presentation.controllers;

import com.example.innkeeper.application.service.HospedeService;
import com.example.innkeeper.domain.entities.Hospede;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hospede")
@Tag(
        name = "Hospedes",
        description = "Operações relacionadas ao gerenciamento de hóspedes."
)
public class HospedeController {

    private final HospedeService hospedeService;

    // Injeção via construtor do Service (Adeus Repository na Controller!)
    public HospedeController(HospedeService hospedeService) {
        this.hospedeService = hospedeService;
    }

    @GetMapping
    @Operation(
            summary = "Listar hóspedes",
            description = "Retorna todos os hóspedes cadastrados no sistema."
    )
    public ResponseEntity<List<Hospede>> listarTodos() {
        return ResponseEntity.ok(hospedeService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar hóspede por id",
            description = "Retorna um hóspede específico através do seu id."
    )
    public ResponseEntity<Hospede> buscarPorId(@PathVariable Long id) {
        Optional<Hospede> hospedeBanco = hospedeService.buscarPorId(id);
        return hospedeBanco.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Criar hóspede",
            description = "Efetua a criação de um novo hóspede."
    )
    public ResponseEntity<Hospede> criar(@RequestBody Hospede hospede) {
        Hospede hospedeBanco = hospedeService.salvar(hospede);
        return ResponseEntity.status(HttpStatus.CREATED).body(hospedeBanco);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar hóspede",
            description = "Atualiza todos os dados de um hóspede existente."
    )
    public ResponseEntity<Hospede> atualizar(@PathVariable Long id, @RequestBody Hospede hospede) {
        Optional<Hospede> hospedeBanco = hospedeService.buscarPorId(id);

        if (hospedeBanco.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Hospede hospedeAtual = hospedeBanco.get();

        // Utilizando Setters corretos por conta do encapsulamento e Value Objects
        hospedeAtual.setNome(hospede.getNome());
        hospedeAtual.setCpf(hospede.getCpf());
        hospedeAtual.setTelefone(hospede.getTelefone());
        hospedeAtual.setEmail(hospede.getEmail());
        hospedeAtual.setStatus(hospede.getStatus());

        return ResponseEntity.ok(hospedeService.salvar(hospedeAtual));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar hóspede parcialmente",
            description = "Atualiza apenas os campos informados de um hóspede existente."
    )
    public ResponseEntity<Hospede> atualizarParcial(@PathVariable Long id, @RequestBody Hospede hospede) {
        Optional<Hospede> hospedeBanco = hospedeService.buscarPorId(id);

        if (hospedeBanco.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Hospede hospedeAtual = hospedeBanco.get();

        if (hospede.getNome() != null) {
            hospedeAtual.setNome(hospede.getNome());
        }
        if (hospede.getCpf() != null) {
            hospedeAtual.setCpf(hospede.getCpf());
        }
        if (hospede.getTelefone() != null) {
            hospedeAtual.setTelefone(hospede.getTelefone());
        }
        if (hospede.getEmail() != null) {
            hospedeAtual.setEmail(hospede.getEmail());
        }
        if (hospede.getStatus() != null) {
            hospedeAtual.setStatus(hospede.getStatus());
        }

        return ResponseEntity.ok(hospedeService.salvar(hospedeAtual));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir hóspede",
            description = "Efetua a exclusão de um hóspede do sistema."
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!hospedeService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }

        hospedeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}