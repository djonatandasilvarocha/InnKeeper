package com.example.innkeeper.controllers;

import com.example.innkeeper.entities.Hospede;
import com.example.innkeeper.repository.HospedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hospedes")
public class HospedeController {

    @Autowired
    private HospedeRepository hospedeRepository;

    @GetMapping
    public ResponseEntity<?> listarTodos() {

        return ResponseEntity.ok(hospedeRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Hospede> criar(@RequestBody Hospede hospede) {

        var hospedeBanco = hospedeRepository.save(hospede);
        return ResponseEntity.ok(hospedeBanco);
    }
}