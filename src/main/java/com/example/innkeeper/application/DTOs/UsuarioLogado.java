package com.example.innkeeper.application.DTOs;

import com.example.innkeeper.domain.entities.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

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
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
    }
}