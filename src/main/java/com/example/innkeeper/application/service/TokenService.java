package com.example.innkeeper.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.innkeeper.application.DTOs.LoginRequest;
import com.example.innkeeper.application.DTOs.UsuarioLogado;
import com.example.innkeeper.domain.entities.Token;
import com.example.innkeeper.domain.repository.TokenRepository;
import com.example.innkeeper.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${spring.secret}")
    private String secret;

    @Value("${spring.expiracao}")
    private Long tempo;

    @Value("${spring.emissor}")
    private String emissor;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public String gerarToken(LoginRequest loginRequestDto) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        var usuario = usuarioRepository.findAll()
                .stream().filter(u -> u.getEmail().equals(loginRequestDto.email()))
                .findFirst().orElse(null);

        String token = JWT.create()
                .withIssuer(emissor)
                .withSubject(loginRequestDto.email())
                .withExpiresAt(this.gerarDataExpiracao())
                .sign(algorithm);

        if (usuario != null) {
            tokenRepository.save(new Token(token, usuario));
        }

        return token;
    }

    public UsuarioLogado verificarToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        JWTVerifier verificador = JWT.require(algorithm).withIssuer(emissor).build();

        verificador.verify(token);

        var tokenBanco = tokenRepository.findTokenByToken(token).orElse(null);

        if (tokenBanco == null) {
            throw new IllegalArgumentException("Token Inválido!");
        }

        return new UsuarioLogado(tokenBanco.getUsuario());
    }

    private Instant gerarDataExpiracao() {
        var dataAtual = LocalDateTime.now();
        dataAtual = dataAtual.plusMinutes(tempo);

        return dataAtual.toInstant(ZoneOffset.of("-03:00"));
    }
}