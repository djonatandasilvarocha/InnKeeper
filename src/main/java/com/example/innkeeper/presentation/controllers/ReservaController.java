package com.example.innkeeper.presentation.controllers;

import com.example.innkeeper.application.service.ReservaService;
import com.example.innkeeper.domain.entities.Reserva;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reserva")
@CrossOrigin(origins = "*")
@Tag(
        name = "Reservas",
        description = "Operações relacionadas ao gerenciamento das reservas."
)
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    @Operation(
            summary = "Listar reservas",
            description = "Retorna todas as reservas cadastradas no sistema."
    )
    public ResponseEntity<List<Reserva>> listarTodos() {
        return ResponseEntity.ok(reservaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar reserva por id",
            description = "Retorna uma reserva específica através do seu id."
    )
    public ResponseEntity<Reserva> buscarPorId(@PathVariable Long id) {
        Optional<Reserva> reservaBanco = reservaService.buscarPorId(id);
        return reservaBanco.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Criar reserva",
            description = "Efetua a criação de uma nova reserva."
    )
    public ResponseEntity<Reserva> criar(@RequestBody Reserva reserva) {
        Reserva reservaBanco = reservaService.salvar(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaBanco);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar reserva",
            description = "Atualiza todos os dados de uma reserva existente."
    )
    public ResponseEntity<Reserva> atualizar(@PathVariable Long id, @RequestBody Reserva reserva) {
        try {
            Optional<Reserva> reservaBanco = reservaService.buscarPorId(id);

            if (reservaBanco.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Reserva reservaAtual = reservaBanco.get();

            reservaAtual.setDataEntrada(reserva.getDataEntrada());
            reservaAtual.setDataSaida(reserva.getDataSaida());
            reservaAtual.setValorTotal(reserva.getValorTotal());
            reservaAtual.setStatus(reserva.getStatus());
            reservaAtual.setHospede(reserva.getHospede());
            reservaAtual.setQuarto(reserva.getQuarto());

            return ResponseEntity.ok(reservaService.salvar(reservaAtual));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar reserva parcialmente",
            description = "Atualiza apenas os campos informados de uma reserva existente."
    )
    public ResponseEntity<Reserva> atualizarParcial(@PathVariable Long id, @RequestBody Reserva reserva) {

        Optional<Reserva> reservaBanco = reservaService.buscarPorId(id);

        if (reservaBanco.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Reserva reservaAtual = reservaBanco.get();

        if (reserva.getDataEntrada() != null) {
            reservaAtual.setDataEntrada(reserva.getDataEntrada());
        }

        if (reserva.getDataSaida() != null) {
            reservaAtual.setDataSaida(reserva.getDataSaida());
        }

        if (reserva.getStatus() != null) {
            reservaAtual.setStatus(reserva.getStatus());
        }

        if (reserva.getHospede() != null) {
            reservaAtual.setHospede(reserva.getHospede());
        }

        if (reserva.getQuarto() != null) {
            reservaAtual.setQuarto(reserva.getQuarto());
        }

        return ResponseEntity.ok(reservaService.salvar(reservaAtual));
    }

    @PatchMapping("/{id}/checkout")
    @Operation(
            summary = "Realizar checkout",
            description = "Finaliza a reserva, calcula o valor total da estadia e libera o quarto."
    )
    public ResponseEntity<Reserva> checkout(@PathVariable Long id) {
        try {
            Optional<Reserva> reservaCheckout = reservaService.realizarCheckout(id);
            return reservaCheckout.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir reserva",
            description = "Efetua a exclusão de uma reserva do sistema."
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!reservaService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }

        reservaService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}