package com.example.innkeeper.presentation.controllers;

import com.example.innkeeper.application.service.QuartoService;
import com.example.innkeeper.domain.entities.Quarto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<Quarto>> listarTodos() {
        return ResponseEntity.ok(quartoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar quarto por id",
            description = "Retorna um quarto específico através do seu id."
    )
    public ResponseEntity<Quarto> buscarPorId(@PathVariable Long id) {
        Optional<Quarto> quartoBanco = quartoService.buscarPorId(id);
        return quartoBanco.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Criar quarto",
            description = "Efetua a criação de um novo quarto."
    )
    public ResponseEntity<Quarto> criar(@RequestBody Quarto quarto) {
        Quarto quartoBanco = quartoService.salvar(quarto);
        return ResponseEntity.status(HttpStatus.CREATED).body(quartoBanco);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar quarto",
            description = "Atualiza todos os dados de um quarto existente."
    )
    public ResponseEntity<Quarto> atualizar(@PathVariable Long id, @RequestBody Quarto quarto) {
        try {
            Optional<Quarto> quartoBanco = quartoService.buscarPorId(id);

            if (quartoBanco.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Quarto quartoAtual = quartoBanco.get();

            quartoAtual.setNumero(quarto.getNumero());
            quartoAtual.setTipo(quarto.getTipo());
            quartoAtual.setCapacidade(quarto.getCapacidade());
            quartoAtual.setDiaria(quarto.getDiaria());
            quartoAtual.setStatus(quarto.getStatus());

            return ResponseEntity.ok(quartoService.salvar(quartoAtual));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar quarto parcialmente",
            description = "Atualiza apenas os campos informados de um quarto existente."
    )
    public ResponseEntity<Quarto> atualizarParcial(@PathVariable Long id, @RequestBody Quarto quarto) {
        Optional<Quarto> quartoBanco = quartoService.buscarPorId(id);

        if (quartoBanco.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Quarto quartoAtual = quartoBanco.get();

        if (quarto.getNumero() != null) {
            quartoAtual.setNumero(quarto.getNumero());
        }

        if (quarto.getTipo() != null) {
            quartoAtual.setTipo(quarto.getTipo());
        }

        if (quarto.getCapacidade() != null) {
            quartoAtual.setCapacidade(quarto.getCapacidade());
        }

        if (quarto.getDiaria() != null) {
            quartoAtual.setDiaria(quarto.getDiaria());
        }

        if (quarto.getStatus() != null) {
            quartoAtual.setStatus(quarto.getStatus());
        }

        return ResponseEntity.ok(quartoService.salvar(quartoAtual));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir quarto",
            description = "Efetua a exclusão de um quarto do sistema."
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!quartoService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }

        quartoService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}