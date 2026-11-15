package com.example.innkeeper.application.DTOs;

import com.example.innkeeper.domain.entities.Usuario;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record UsuarioLogado(
        Long id,
        String email,
        String cpf,
        Collection<? extends GrantedAuthority> authorities
) {
    public UsuarioLogado(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getAuthorities()
        );
    }
}