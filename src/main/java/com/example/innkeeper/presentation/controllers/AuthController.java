package com.example.innkeeper.presentation.controllers;

import com.example.innkeeper.application.DTOs.LoginRequest;
import com.example.innkeeper.application.DTOs.LoginResponse;
import com.example.innkeeper.application.DTOs.UsuarioResponse;
import com.example.innkeeper.application.service.TokenService;
import com.example.innkeeper.domain.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        var usuario = usuarioRepository.findByEmail(loginRequest.email())
                .orElse(null);

        if (usuario == null || !usuario.getSenha().equals(loginRequest.senha())) {
            return ResponseEntity.badRequest().body("Usuario ou senha invalido!");
        }

        if (usuario.getStatus() != com.example.innkeeper.domain.entities.EnumStatusUsuario.ATIVO) {
            return ResponseEntity.badRequest().body("Usuário não está ativo!");
        }

        var token = tokenService.gerarToken(loginRequest);

        var usuarioResponse = new UsuarioResponse(usuario);

        return ResponseEntity.ok(
                new LoginResponse(token, usuarioResponse)
        );
    }
}