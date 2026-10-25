package com.example.innkeeper.domain.entities;

import com.example.innkeeper.application.DTOs.CriarAdminRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cpf;

    private String senha;

    private String email;

    private String role = "ROLE_USER";

    private EnumStatusUsuario status = EnumStatusUsuario.ATIVO;

    public Usuario(CriarAdminRequest criarAdminRequest) {
        this.setCpf(criarAdminRequest.cpf());
        this.setNome(criarAdminRequest.nome());
        this.setSenha(criarAdminRequest.senha());
        this.setEmail(criarAdminRequest.email());
        this.setRole("ROLE_ADMIN");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role.equalsIgnoreCase("ROLE_ADMIN") || this.role.equalsIgnoreCase("ADMINISTRADOR")) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_FUNCIONARIO"),
                    new SimpleGrantedAuthority("ROLE_HOSPEDE")
            );
        } else if (this.role.equalsIgnoreCase("ROLE_FUNCIONARIO") || this.role.equalsIgnoreCase("FUNCIONARIO")) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_FUNCIONARIO"),
                    new SimpleGrantedAuthority("ROLE_HOSPEDE")
            );
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_HOSPEDE"));
        }
    }

    @Override
    public @Nullable String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}