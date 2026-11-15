package com.example.innkeeper.presentation.controllers;

import com.example.innkeeper.application.DTOs.AtualizarHospedeRequest;
import com.example.innkeeper.application.DTOs.CriarHospedeRequest;
import com.example.innkeeper.application.DTOs.HospedeResponse;
import com.example.innkeeper.application.service.HospedeService;
// O import de com.example.innkeeper.domain.entities.Hospede é removido da apresentação!

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospede")
@Tag(
        name = "Hospedes",
        description = "Operações relacionadas ao gerenciamento de hóspedes."
)
public class HospedeController {

    private final HospedeService hospedeService;

    public HospedeController(HospedeService hospedeService) {
        this.hospedeService = hospedeService;
    }

    @GetMapping
    @Operation(
            summary = "Listar hóspedes",
            description = "Retorna todos os hóspedes cadastrados no sistema."
    )
    public ResponseEntity<List<HospedeResponse>> listarTodos() {
        return ResponseEntity.ok(hospedeService.listarTodosDto());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar hóspede por id",
            description = "Retorna um hóspede específico através do seu id."
    )
    public ResponseEntity<HospedeResponse> buscarPorId(@PathVariable Long id) {
        HospedeResponse hospedeResponse = hospedeService.buscarPorIdDto(id);
        if (hospedeResponse != null) {
            return ResponseEntity.ok(hospedeResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Criar hóspede",
            description = "Efetua a criação de um novo hóspede."
    )
    public ResponseEntity<HospedeResponse> criar(@RequestBody CriarHospedeRequest request) {
        HospedeResponse response = hospedeService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar hóspede",
            description = "Atualiza todos os dados de um hóspede existente."
    )
    public ResponseEntity<HospedeResponse> atualizar(@PathVariable Long id, @RequestBody AtualizarHospedeRequest request) {
        HospedeResponse response = hospedeService.atualizarDto(id, request);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar hóspede parcialmente",
            description = "Atualiza apenas os campos informados de um hóspede existente."
    )
    public ResponseEntity<HospedeResponse> atualizarParcial(@PathVariable Long id, @RequestBody AtualizarHospedeRequest request) {
        HospedeResponse response = hospedeService.atualizarParcialDto(id, request);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir hóspede",
            description = "Efetua a exclusão de um hóspede do sistema."
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = hospedeService.deletarPorId(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}