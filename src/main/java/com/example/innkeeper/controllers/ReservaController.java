package com.example.innkeeper.controllers;

import com.example.innkeeper.entities.Reserva;
import com.example.innkeeper.repository.ReservaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
@Tag(
        name = "Reservas",
        description = "Operações relacionadas ao gerenciamento das reservas."
)
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @Operation(
            summary = "Listar reservas",
            description = "Retorna todas as reservas cadastradas no sistema."
    )
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(reservaRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Reserva> criar(@RequestBody Reserva reserva) {

        var reservaBanco = reservaRepository.save(reserva);

        return ResponseEntity.ok(reservaBanco);
    }

    // PUT - Atualizar reserva completa
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> atualizar(
            @PathVariable Long id,
            @RequestBody Reserva reserva) {

        var reservaBanco = reservaRepository.findById(id);

        if (reservaBanco.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Reserva reservaAtual = reservaBanco.get();

        reservaAtual.dataEntrada = reserva.dataEntrada;
        reservaAtual.dataSaida = reserva.dataSaida;
        reservaAtual.valorTotal = reserva.valorTotal;
        reservaAtual.status = reserva.status;
        reservaAtual.setHospede(reserva.getHospede());
        reservaAtual.setQuarto(reserva.getQuarto());

        return ResponseEntity.ok(reservaRepository.save(reservaAtual));
    }

    // PATCH - Atualizar reserva parcialmente
    @PatchMapping("/{id}")
    public ResponseEntity<Reserva> atualizarParcial(
            @PathVariable Long id,
            @RequestBody Reserva reserva) {

        var reservaBanco = reservaRepository.findById(id);

        if (reservaBanco.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Reserva reservaAtual = reservaBanco.get();

        if (reserva.dataEntrada != null) {
            reservaAtual.dataEntrada = reserva.dataEntrada;
        }

        if (reserva.dataSaida != null) {
            reservaAtual.dataSaida = reserva.dataSaida;
        }

        if (reserva.valorTotal != null) {
            reservaAtual.valorTotal = reserva.valorTotal;
        }

        if (reserva.status != null) {
            reservaAtual.status = reserva.status;
        }

        if (reserva.getHospede() != null) {
            reservaAtual.setHospede(reserva.getHospede());
        }

        if (reserva.getQuarto() != null) {
            reservaAtual.setQuarto(reserva.getQuarto());
        }

        return ResponseEntity.ok(reservaRepository.save(reservaAtual));
    }

    // DELETE - Excluir reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        if (!reservaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        reservaRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}