package com.example.innkeeper.controllers;

import com.example.innkeeper.DTOs.EsqueciSenhaRequest;
import com.example.innkeeper.DTOs.LoginRequest;
import com.example.innkeeper.DTOs.LoginResponse;
import com.example.innkeeper.DTOs.RedefinirSenhaRequest;
import com.example.innkeeper.entities.Usuario;
import com.example.innkeeper.repository.UsuarioRepository;
import com.example.innkeeper.service.RecuperarSenhaService;
import com.example.innkeeper.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;

@RestController
@RequestMapping("/auth")
@Tag(
        description = "Controller de autenticação!",
        name = "Autenticação"
)
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RecuperarSenhaService recuperarSenhaService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    @Operation(
            description = "Metodo de login",
            summary = "Autenticação de usuarios"
    )
    public ResponseEntity<?> login(
            @RequestBody LoginRequest loginRequest) {

        Usuario usuario = usuarioRepository
                .findByEmail(loginRequest.email())
                .orElse(null);

        if (usuario != null &&
                passwordEncoder.matches(
                        loginRequest.senha(),
                        usuario.getSenha())) {

            var token = tokenService.gerarToken(
                    usuario.getEmail()
            );

            return ResponseEntity.ok(
                    new LoginResponse(token)
            );
        }

        return ResponseEntity.badRequest()
                .body("Usuario ou senha invalido!");
    }


    @PostMapping("/esqueci-senha")
    @Operation(
            summary = "Solicitar recuperação de senha",
            description = "Gera um token temporário para recuperação da senha do usuário."
    )
    public ResponseEntity<?> esqueciSenha(
            @Valid @RequestBody EsqueciSenhaRequest request) {

        recuperarSenhaService.solicitarRecuperacao(
                request.email()
        );

        return ResponseEntity.ok(
                "Se o email estiver cadastrado, você receberá as instruções para redefinir sua senha."
        );
    }


    @PostMapping("/redefinir-senha")
    @Operation(
            summary = "Redefinir senha",
            description = "Redefine a senha do usuário utilizando um token temporário."
    )
    public ResponseEntity<?> redefinirSenha(
            @Valid @RequestBody RedefinirSenhaRequest request) {

        recuperarSenhaService.redefinirSenha(
                request.token(),
                request.novaSenha(),
                request.confirmarSenha()
        );

        return ResponseEntity.ok(
                "Senha redefinida com sucesso!"
        );
    }
}
