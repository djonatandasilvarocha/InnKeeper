package com.example.innkeeper.controllers;

import com.example.innkeeper.entities.Quarto;
import com.example.innkeeper.repository.QuartoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quarto")
@CrossOrigin(origins = "*")
@Tag(
        name = "Quartos",
        description = "Operações relacionadas ao gerenciamento dos quartos da pousada."
)
public class QuartoController {

    @Autowired
    private QuartoRepository quartoRepository;

    @GetMapping
    @Operation(
            summary = "Listar quartos",
            description = "Retorna todos os quartos cadastrados no sistema."
    )
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(quartoRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar quarto por id",
            description = "Retorna um quarto específico através do seu id."
    )
    public ResponseEntity<Quarto> buscarPorId(@PathVariable Long id) {
        Quarto quartoBanco = quartoRepository.findById(id).orElse(null);
        if (quartoBanco != null) {
            return ResponseEntity.ok(quartoBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Criar quarto",
            description = "Efetua a criação de um novo quarto."
    )
    public ResponseEntity<Quarto> criar(@RequestBody Quarto quarto) {
        var quartoBanco = quartoRepository.save(quarto);
        return ResponseEntity.ok(quartoBanco);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar quarto",
            description = "Atualiza todos os dados de um quarto existente."
    )
    public ResponseEntity<Quarto> atualizar(@PathVariable Long id, @RequestBody Quarto quarto) {

        try {
            var quartoBanco = quartoRepository.findById(id);

            if (quartoBanco.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Quarto quartoAtual = quartoBanco.get();

            quartoAtual.numero = quarto.numero;
            quartoAtual.tipo = quarto.tipo;
            quartoAtual.capacidade = quarto.capacidade;
            quartoAtual.diaria = quarto.diaria;
            quartoAtual.status = quarto.status;

            return ResponseEntity.ok(quartoRepository.save(quartoAtual));
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

        var quartoBanco = quartoRepository.findById(id);

        if (quartoBanco.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Quarto quartoAtual = quartoBanco.get();

        if (quarto.numero != null) {
            quartoAtual.numero = quarto.numero;
        }

        if (quarto.tipo != null) {
            quartoAtual.tipo = quarto.tipo;
        }

        if (quarto.capacidade != null) {
            quartoAtual.capacidade = quarto.capacidade;
        }

        if (quarto.diaria != null) {
            quartoAtual.diaria = quarto.diaria;
        }

        if (quarto.status != null) {
            quartoAtual.status = quarto.status;
        }

        return ResponseEntity.ok(quartoRepository.save(quartoAtual));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir quarto",
            description = "Efetua a exclusão de um quarto do sistema."
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        if (!quartoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        quartoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}