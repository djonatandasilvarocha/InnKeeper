package com.example.innkeeper.presentation.controllers;

import com.example.innkeeper.application.DTOs.LoginRequest;
import com.example.innkeeper.application.DTOs.LoginResponse;
import com.example.innkeeper.application.service.TokenService;
import com.example.innkeeper.domain.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Controller de autenticação")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    @Operation(description = "Método de login", summary = "Autenticação de usuarios")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        if (usuarioRepository.existsUsuarioByEmailAndSenha(loginRequest.email(), loginRequest.senha())) {
            // Gera o Token salvando no banco e o retorna
            var token = tokenService.gerarToken(loginRequest);

            return ResponseEntity.ok(new LoginResponse(token));
        }
        return ResponseEntity.badRequest().body("Usuario ou senha invalido!");
    }
}