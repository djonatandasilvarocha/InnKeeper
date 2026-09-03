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
@RequestMapping("/quartos")
@Tag(
        name = "Quartos",
        description = "Operações relacionadas ao gerenciamento dos quartos da pousada."
)
public class QuartoController {

    @Autowired
    private QuartoRepository quartoRepository;

    @Operation(
            summary = "Listar quartos",
            description = "Retorna todos os quartos cadastrados no sistema."
    )
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(quartoRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Quarto> criar(@RequestBody Quarto quarto) {
        var quartoBanco = quartoRepository.save(quarto);
        return ResponseEntity.ok(quartoBanco);
    }

    // PUT - Atualizar quarto completo
    @PutMapping("/{id}")
    public ResponseEntity<Quarto> atualizar(
            @PathVariable Long id,
            @RequestBody Quarto quarto) {

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
    }

    // PATCH - Atualizar quarto parcialmente
    @PatchMapping("/{id}")
    public ResponseEntity<Quarto> atualizarParcial(
            @PathVariable Long id,
            @RequestBody Quarto quarto) {

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

    // DELETE - Excluir quarto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        if (!quartoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        quartoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}