package com.example.innkeeper.service;

import com.example.innkeeper.entities.EnumStatusUsuario;
import com.example.innkeeper.entities.Usuario;
import com.example.innkeeper.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RecuperarSenhaService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public RecuperarSenhaService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void solicitarRecuperacao(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElse(null);

        // Não revela se o email existe
        if (usuario == null) {
            return;
        }

        // Usuário excluído não pode recuperar senha
        if (usuario.getStatus() == EnumStatusUsuario.EXCLUIDO) {
            return;
        }

        String token = UUID.randomUUID().toString();

        usuario.setTokenRecuperacaoSenha(token);

        usuario.setExpiracaoTokenRecuperacao(
                LocalDateTime.now().plusMinutes(15)
        );

        usuarioRepository.save(usuario);

        // Temporariamente vamos mostrar no console
        System.out.println("======================================");
        System.out.println("TOKEN DE RECUPERAÇÃO DE SENHA:");
        System.out.println(token);
        System.out.println("======================================");
    }

    public void redefinirSenha(
            String token,
            String novaSenha,
            String confirmarSenha) {

        if (!novaSenha.equals(confirmarSenha)) {
            throw new RuntimeException(
                    "As senhas não coincidem"
            );
        }

        Usuario usuario = usuarioRepository
                .findByTokenRecuperacaoSenha(token)
                .orElseThrow(() ->
                        new RuntimeException("Token inválido"));

        if (usuario.getExpiracaoTokenRecuperacao() == null ||
                usuario.getExpiracaoTokenRecuperacao()
                        .isBefore(LocalDateTime.now())) {

            throw new RuntimeException("Token expirado");
        }

        usuario.setSenha(
                passwordEncoder.encode(novaSenha)
        );

        // Invalida o token depois da utilização
        usuario.setTokenRecuperacaoSenha(null);
        usuario.setExpiracaoTokenRecuperacao(null);

        usuarioRepository.save(usuario);
    }
}
