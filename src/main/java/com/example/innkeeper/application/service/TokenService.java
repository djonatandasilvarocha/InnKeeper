package com.example.innkeeper.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.innkeeper.domain.entities.Token;
import com.example.innkeeper.domain.entities.Usuario;
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
    private Long expiracao;

    @Value("${spring.emissor}")
    private String emissor;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public String gerarToken(String email) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElse(null);

        String token = JWT.create()
                .withIssuer(emissor)
                .withSubject(email)
                .withExpiresAt(this.getDataExpiracao())
                .sign(algorithm);

        if (usuario != null) {
            tokenRepository.save(new Token(token, usuario));
        }

        return token;
    }

    public DecodedJWT verificarToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verificador = JWT.require(algorithm).withIssuer(emissor).build();
        return verificador.verify(token);
    }

    private Instant getDataExpiracao() {
        var dataAtual = LocalDateTime.now();
        var dataFutura = dataAtual.plusMinutes(expiracao);
        return dataFutura.toInstant(ZoneOffset.of("-03:00"));
    }
}