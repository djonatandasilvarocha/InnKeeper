package com.example.innkeeper.controllers;

import com.example.innkeeper.entities.EnumStatusReserva;
import com.example.innkeeper.entities.Reserva;
import com.example.innkeeper.repository.ReservaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/reservas")
@Tag(
        name = "Reservas",
        description = "Operações relacionadas ao gerenciamento das reservas."
)
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @GetMapping
    @Operation(
            summary = "Listar reservas",
            description = "Retorna todas as reservas cadastradas no sistema."
    )
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(reservaRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar reserva por id",
            description = "Retorna uma reserva específica através do seu id."
    )
    public ResponseEntity<Reserva> buscarPorId(@PathVariable Long id) {
        Reserva reservaBanco = reservaRepository.findById(id).orElse(null);
        if (reservaBanco != null) {
            return ResponseEntity.ok(reservaBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Criar reserva",
            description = "Efetua a criação de uma nova reserva."
    )
    public ResponseEntity<Reserva> criar(@RequestBody Reserva reserva) {
        var reservaBanco = reservaRepository.save(reserva);
        return ResponseEntity.ok(reservaBanco);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar reserva",
            description = "Atualiza todos os dados de uma reserva existente."
    )
    public ResponseEntity<Reserva> atualizar(@PathVariable Long id, @RequestBody Reserva reserva) {

        try {
            var reservaBanco = reservaRepository.findById(id);

            if (reservaBanco.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Reserva reservaAtual = reservaBanco.get();

            reservaAtual.dataEntrada = reserva.dataEntrada;
            reservaAtual.dataSaida = reserva.dataSaida;
            reservaAtual.valorTotal = reserva.valorTotal;
            reservaAtual.status = reserva.status;
            reservaAtual.hospede = reserva.hospede;
            reservaAtual.quarto = reserva.quarto;

            return ResponseEntity.ok(reservaRepository.save(reservaAtual));
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

        if (reserva.status != null) {
            reservaAtual.status = reserva.status;
        }

        if (reserva.hospede != null) {
            reservaAtual.hospede = reserva.hospede;
        }

        if (reserva.quarto != null) {
            reservaAtual.quarto = reserva.quarto;
        }

        return ResponseEntity.ok(reservaRepository.save(reservaAtual));
    }

    @PatchMapping("/{id}/checkout")
    @Operation(
            summary = "Realizar checkout",
            description = "Finaliza a reserva, calcula o valor total da estadia e libera o quarto."
    )
    public ResponseEntity<Reserva> checkout(@PathVariable Long id) {

        var reservaBanco = reservaRepository.findById(id);

        if (reservaBanco.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Reserva reserva = reservaBanco.get();

        if (reserva.status == EnumStatusReserva.FINALIZADA) {
            return ResponseEntity.badRequest().build();
        }

        if (reserva.status == EnumStatusReserva.CANCELADA) {
            return ResponseEntity.badRequest().build();
        }

        long dias = ChronoUnit.DAYS.between(reserva.dataEntrada, reserva.dataSaida);

        BigDecimal diaria = reserva.quarto.diaria;

        BigDecimal valorTotal = diaria.multiply(BigDecimal.valueOf(dias));

        reserva.valorTotal = valorTotal;
        reserva.status = EnumStatusReserva.FINALIZADA;
        reserva.quarto.status = com.example.innkeeper.entities.EnumStatusQuarto.DISPONIVEL;

        return ResponseEntity.ok(reservaRepository.save(reserva));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir reserva",
            description = "Efetua a exclusão de uma reserva do sistema."
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        if (!reservaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        reservaRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}