package com.example.innkeeper.domain.repository;

import com.example.innkeeper.domain.entities.EnumStatusUsuario;
import com.example.innkeeper.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<List<Usuario>> findByStatusNot(EnumStatusUsuario status);

    Optional<Usuario> findByEmail(String email);

    boolean existsUsuarioByEmailAndSenha(String email, String senha);
}