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

    public LoginResponse validarUsuarioAutenticadoRetornaToken(LoginRequest request) {
        if (usuarioRepository.existsUsuarioByEmailAndSenha(request.email(), request.senha())) {
            var token = tokenService.gerarToken(request);
            return new LoginResponse(token);
        }
        return null;
    }

    public List<UsuarioResponse> listarTodosUsuariosTable() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }

    public CriarAdminResponse criarAdmin(CriarAdminRequest criarAdminRequest) {
        if (!criarAdminRequest.secretKey().equals(secret)) {
            return new CriarAdminResponse(0L, "Chave secreta inválida!");
        }

        Usuario usuarioAdminSalvar = new Usuario(criarAdminRequest);
        usuarioRepository.save(usuarioAdminSalvar);

        return new CriarAdminResponse(usuarioAdminSalvar.getId(), "Usuário salvo com sucesso!");
    }

    public UsuarioLogado getUsuarioLogado() {
        return (UsuarioLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public UsuarioResponse salvarUsuario(CriarUsuarioRequest usuario, Long id) {
        var usuarioLogado = getUsuarioLogado();

        // Novo Usuário
        if (id == 0L) {
            Usuario usuarioBanco = new Usuario();
            usuarioBanco.setNome(usuario.nome());
            usuarioBanco.setCpf(usuario.cpf());
            usuarioBanco.setEmail(usuario.email());
            usuarioBanco.setSenha(usuario.senha());

            usuarioRepository.save(usuarioBanco);
            return new UsuarioResponse(usuarioBanco);
        } else {
            // Usuário existente
            Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
            if (usuarioBanco != null) {
                usuarioBanco.setNome(usuario.nome());
                usuarioBanco.setCpf(usuario.cpf());
                usuarioBanco.setEmail(usuario.email());
                usuarioBanco.setSenha(usuario.senha());
                usuarioRepository.save(usuarioBanco);
                return new UsuarioResponse(usuarioBanco);
            }
        }

        return null;
    }

    public UsuarioResponse buscarPorId(Long id) {
        var usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return null;
        }
        return new UsuarioResponse(usuario);
    }

    public boolean atualizarStatus(EnumStatusUsuario status, Long id) {
        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if (usuarioBanco != null) {
            usuarioBanco.setStatus(status);
            usuarioRepository.save(usuarioBanco);
            return true;
        }
        return false;
    }
}