package com.example.innkeeper.presentation.controllers;

import com.example.innkeeper.application.DTOs.AtualizarQuartoRequest;
import com.example.innkeeper.application.DTOs.CriarQuartoRequest;
import com.example.innkeeper.application.DTOs.QuartoResponse;
import com.example.innkeeper.application.service.QuartoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quarto")
@CrossOrigin(origins = "*")
@Tag(
        name = "Quartos",
        description = "Operações relacionadas ao gerenciamento dos quartos da pousada."
)
public class QuartoController {

    private final QuartoService quartoService;

    public QuartoController(QuartoService quartoService) {
        this.quartoService = quartoService;
    }

    @GetMapping
    @Operation(
            summary = "Listar quartos",
            description = "Retorna todos os quartos cadastrados no sistema."
    )
    public ResponseEntity<List<QuartoResponse>> listarTodos() {
        return ResponseEntity.ok(quartoService.listarTodosDto());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar quarto por id",
            description = "Retorna um quarto específico através do seu id."
    )
    public ResponseEntity<QuartoResponse> buscarPorId(@PathVariable Long id) {
        QuartoResponse quartoResponse = quartoService.buscarPorIdDto(id);
        if (quartoResponse != null) {
            return ResponseEntity.ok(quartoResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Criar quarto",
            description = "Efetua a criação de um novo quarto."
    )
    public ResponseEntity<QuartoResponse> criar(@RequestBody CriarQuartoRequest request) {
        QuartoResponse quartoBanco = quartoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(quartoBanco);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar quarto",
            description = "Atualiza todos os dados de um quarto existente."
    )
    public ResponseEntity<QuartoResponse> atualizar(@PathVariable Long id, @RequestBody AtualizarQuartoRequest request) {
        try {
            QuartoResponse quartoAtualizado = quartoService.atualizarDto(id, request);
            if (quartoAtualizado != null) {
                return ResponseEntity.ok(quartoAtualizado);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar quarto parcialmente",
            description = "Atualiza apenas os campos informados de um quarto existente."
    )
    public ResponseEntity<QuartoResponse> atualizarParcial(@PathVariable Long id, @RequestBody AtualizarQuartoRequest request) {
        QuartoResponse quartoAtualizado = quartoService.atualizarParcialDto(id, request);
        if (quartoAtualizado != null) {
            return ResponseEntity.ok(quartoAtualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir quarto",
            description = "Efetua a exclusão de um quarto do sistema."
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = quartoService.deletarPorId(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}