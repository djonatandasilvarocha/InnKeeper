package com.example.innkeeper.controllers;

import com.example.innkeeper.entities.Quarto;
import com.example.innkeeper.repository.QuartoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quartos")
public class QuartoController {

    @Autowired
    private QuartoRepository quartoRepository;

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
}
