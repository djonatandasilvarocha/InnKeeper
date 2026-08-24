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
}
