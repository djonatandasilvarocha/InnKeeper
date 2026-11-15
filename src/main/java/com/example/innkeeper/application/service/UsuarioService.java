
package com.example.innkeeper.application.service;

import com.example.innkeeper.application.DTOs.*;
import com.example.innkeeper.domain.entities.EnumStatusUsuario;
import com.example.innkeeper.domain.entities.Usuario;
import com.example.innkeeper.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Value("${spring.secret}")
    private String secret;

    public LoginResponse validarUsuarioAutenticadoRetornaToken(
            LoginRequest request
    ) {
        var usuario = usuarioRepository.findByEmail(request.email())
                .orElse(null);

        if (usuario == null ||
                !usuario.getSenha().equals(request.senha())) {
            return null;
        }

        if (usuario.getStatus() != EnumStatusUsuario.ATIVO) {
            return null;
        }

        var token = tokenService.gerarToken(request);

        return new LoginResponse(
                token,
                new UsuarioResponse(usuario)
        );
    }

    public List<UsuarioResponse> listarTodosUsuariosTable() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }

    public CriarAdminResponse criarAdmin(
            CriarAdminRequest request
    ) {
        if (request == null ||
                request.secretKey() == null ||
                !request.secretKey().equals(secret)) {
            throw new IllegalArgumentException(
                    "Chave de inicializacao invalida."
            );
        }

        if (usuarioRepository.count() > 0) {
            throw new IllegalStateException(
                    "O administrador inicial ja foi criado."
            );
        }

        if (request.nome() == null ||
                request.nome().isBlank() ||
                request.email() == null ||
                request.email().isBlank() ||
                request.senha() == null ||
                request.senha().isBlank() ||
                request.cpf() == null ||
                request.cpf().isBlank()) {
            throw new IllegalArgumentException(
                    "Preencha todos os campos obrigatorios."
            );
        }

        if (usuarioRepository.findByEmail(request.email())
                .isPresent()) {
            throw new IllegalArgumentException(
                    "Este email ja esta cadastrado."
            );
        }

        Usuario administrador = new Usuario(request);

        usuarioRepository.save(administrador);

        return new CriarAdminResponse(
                administrador.getId(),
                "Administrador criado com sucesso!"
        );
    }

    public UsuarioLogado getUsuarioLogado() {
        return (UsuarioLogado) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public UsuarioResponse salvarUsuario(
            CriarUsuarioRequest request,
            Long id
    ) {
        if (id == 0L) {
            Usuario usuario = new Usuario();

            usuario.setNome(request.nome());
            usuario.setCpf(request.cpf());
            usuario.setEmail(request.email());
            usuario.setSenha(request.senha());

            usuarioRepository.save(usuario);

            return new UsuarioResponse(usuario);
        }

        Usuario usuario = usuarioRepository.findById(id)
                .orElse(null);

        if (usuario == null) {
            return null;
        }

        usuario.setNome(request.nome());
        usuario.setCpf(request.cpf());
        usuario.setEmail(request.email());
        usuario.setSenha(request.senha());

        usuarioRepository.save(usuario);

        return new UsuarioResponse(usuario);
    }

    public UsuarioResponse buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(UsuarioResponse::new)
                .orElse(null);
    }

    public boolean atualizarStatus(
            EnumStatusUsuario status,
            Long id
    ) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElse(null);

        if (usuario == null) {
            return false;
        }

        usuario.setStatus(status);
        usuarioRepository.save(usuario);

        return true;
    }
}
