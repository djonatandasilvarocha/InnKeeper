package com.example.innkeeper.presentation.controllers;

import com.example.innkeeper.application.DTOs.AtualizarReservaRequest;
import com.example.innkeeper.application.DTOs.CriarReservaRequest;
import com.example.innkeeper.application.DTOs.ReservaResponse;
import com.example.innkeeper.application.service.ReservaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reserva")
@Tag(
        name = "Reservas",
        description = "Operações relacionadas ao gerenciamento de reservas da pousada."
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
    public ResponseEntity<List<ReservaResponse>> listarTodas() {
        return ResponseEntity.ok(reservaService.listarTodasDto());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar reserva por id",
            description = "Retorna uma reserva específica através do seu id."
    )
    public ResponseEntity<ReservaResponse> buscarPorId(@PathVariable Long id) {
        ReservaResponse response = reservaService.buscarPorIdDto(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Criar reserva",
            description = "Efetua a criação de uma nova reserva."
    )
    public ResponseEntity<ReservaResponse> criar(@RequestBody CriarReservaRequest request) {
        ReservaResponse response = reservaService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar reserva",
            description = "Atualiza todos os dados de uma reserva existente."
    )
    public ResponseEntity<ReservaResponse> atualizar(@PathVariable Long id, @RequestBody AtualizarReservaRequest request) {
        ReservaResponse response = reservaService.atualizarDto(id, request);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar reserva parcialmente",
            description = "Atualiza apenas os campos informados de uma reserva existente."
    )
    public ResponseEntity<ReservaResponse> atualizarParcial(@PathVariable Long id, @RequestBody AtualizarReservaRequest request) {
        ReservaResponse response = reservaService.atualizarParcialDto(id, request);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/checkout/{id}")
    @Operation(
            summary = "Realizar checkout",
            description = "Efetua o checkout de uma reserva, calculando o valor final e liberando o quarto."
    )
    public ResponseEntity<ReservaResponse> checkout(@PathVariable Long id) {
        try {
            ReservaResponse response = reservaService.realizarCheckoutDto(id);
            if (response != null) {
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.notFound().build();
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
        boolean deletado = reservaService.deletarLogicoOuFisico(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}