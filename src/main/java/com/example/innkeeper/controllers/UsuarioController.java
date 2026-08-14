package com.example.innkeeper.controllers;

import com.example.innkeeper.entities.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        List<Usuario> usuarios = List.of(new Usuario(1L, "Djonatan","11248096940", "123456", "djousr@hotmail.com"));

        return ResponseEntity.ok(usuarios);
    }

}
