
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

    // ==========================================
    // LISTAR RESERVAS
    // ==========================================

    @GetMapping
    @Operation(
            summary = "Listar reservas",
            description = "Retorna as reservas pertencentes ao usuário autenticado."
    )
    public ResponseEntity<List<ReservaResponse>> listarTodas() {

        return ResponseEntity.ok(reservaService.listarTodasDto());
    }

    // ==========================================
    // BUSCAR RESERVA POR ID
    // ==========================================

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar reserva por id",
            description = "Retorna uma reserva específica pertencente ao usuário autenticado."
    )
    public ResponseEntity<ReservaResponse> buscarPorId(@PathVariable Long id) {

        return reservaService.buscarPorIdDto(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ==========================================
    // CRIAR RESERVA
    // ==========================================

    @PostMapping
    @Operation(
            summary = "Criar reserva",
            description = "Efetua a criação de uma nova reserva."
    )
    public ResponseEntity<?> criar(
            @RequestBody CriarReservaRequest request
    ) {

        try {

            ReservaResponse response = reservaService.criar(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ==========================================
    // ATUALIZAR RESERVA COMPLETA
    // ==========================================

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar reserva",
            description = "Atualiza os dados de uma reserva existente."
    )
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody AtualizarReservaRequest request
    ) {

        try {

            ReservaResponse response = reservaService.atualizarDto(id, request);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {

            if ("Reserva não encontrada.".equals(e.getMessage())) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ==========================================
    // ATUALIZAÇÃO PARCIAL
    // ==========================================

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar reserva parcialmente",
            description = "Atualiza apenas os campos informados de uma reserva existente."
    )
    public ResponseEntity<?> atualizarParcial(
            @PathVariable Long id,
            @RequestBody AtualizarReservaRequest request
    ) {

        try {

            ReservaResponse response =
                    reservaService.atualizarParcialDto(id, request);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {

            if ("Reserva não encontrada.".equals(e.getMessage())) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ==========================================
    // CHECKOUT
    // ==========================================

    @PostMapping("/checkout/{id}")
    @Operation(
            summary = "Realizar checkout",
            description = "Finaliza uma reserva, calcula o valor final e libera o quarto quando possível."
    )
    public ResponseEntity<?> checkout(@PathVariable Long id) {

        try {

            ReservaResponse response =
                    reservaService.realizarCheckoutDto(id);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {

            if ("Reserva não encontrada.".equals(e.getMessage())) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ==========================================
    // EXCLUSÃO LÓGICA
    // ==========================================

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir reserva",
            description = "Realiza a exclusão lógica de uma reserva."
    )
    public ResponseEntity<?> deletar(@PathVariable Long id) {

        try {

            reservaService.deletarLogicoOuFisico(id);

            return ResponseEntity.noContent().build();

        } catch (IllegalArgumentException e) {

            if ("Reserva não encontrada.".equals(e.getMessage())) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
