package com.example.innkeeper.presentation.controllers;

import com.example.innkeeper.application.DTOs.HospedeResponse;
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

    // Injeção via construtor do Service
    public HospedeController(HospedeService hospedeService) {
        this.hospedeService = hospedeService;
    }

    @GetMapping
    @Operation(
            summary = "Listar hóspedes",
            description = "Retorna todos os hóspedes cadastrados no sistema."
    )
    public ResponseEntity<List<HospedeResponse>> listarTodos() {
        return ResponseEntity.ok(hospedeService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar hóspede por id",
            description = "Retorna um hóspede específico através do seu id."
    )
    public ResponseEntity<HospedeResponse> buscarPorId(@PathVariable Long id) {
        Optional<HospedeResponse> hospedeBanco = hospedeService.buscarPorId(id);
        return hospedeBanco.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Criar hóspede",
            description = "Efetua a criação de um novo hóspede."
    )
    public ResponseEntity<HospedeResponse> criar(@RequestBody Hospede hospede) {
        Hospede hospedeSalvo = hospedeService.salvar(hospede);
        // Reutilizamos o método de conversão ou construímos o response diretamente
        HospedeResponse response = hospedeService.buscarPorId(hospedeSalvo.getId()).get();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar hóspede",
            description = "Atualiza todos os dados de um hóspede existente."
    )
    public ResponseEntity<HospedeResponse> atualizar(@PathVariable Long id, @RequestBody Hospede hospede) {
        Optional<HospedeResponse> hospedeBanco = hospedeService.buscarPorId(id);

        if (hospedeBanco.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Buscamos a entidade real para alteração
        Hospede entidadeAtual = hospedeService.buscarEntidadePorId(id).get();

        entidadeAtual.setNome(hospede.getNome());
        entidadeAtual.setCpf(hospede.getCpf());
        entidadeAtual.setTelefone(hospede.getTelefone());
        entidadeAtual.setEmail(hospede.getEmail());
        entidadeAtual.setStatus(hospede.getStatus());

        Hospede atualizado = hospedeService.salvar(entidadeAtual);
        HospedeResponse response = hospedeService.buscarPorId(atualizado.getId()).get();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar hóspede parcialmente",
            description = "Atualiza apenas os campos informados de um hóspede existente."
    )
    public ResponseEntity<HospedeResponse> atualizarParcial(@PathVariable Long id, @RequestBody Hospede hospede) {
        Optional<HospedeResponse> hospedeBanco = hospedeService.buscarPorId(id);

        if (hospedeBanco.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Hospede entidadeAtual = hospedeService.buscarEntidadePorId(id).get();

        if (hospede.getNome() != null) {
            entidadeAtual.setNome(hospede.getNome());
        }
        if (hospede.getCpf() != null) {
            entidadeAtual.setCpf(hospede.getCpf());
        }
        if (hospede.getTelefone() != null) {
            entidadeAtual.setTelefone(hospede.getTelefone());
        }
        if (hospede.getEmail() != null) {
            entidadeAtual.setEmail(hospede.getEmail());
        }
        if (hospede.getStatus() != null) {
            entidadeAtual.setStatus(hospede.getStatus());
        }

        Hospede atualizado = hospedeService.salvar(entidadeAtual);
        HospedeResponse response = hospedeService.buscarPorId(atualizado.getId()).get();

        return ResponseEntity.ok(response);
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