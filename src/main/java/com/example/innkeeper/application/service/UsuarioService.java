package com.example.innkeeper.application.service;

import com.example.innkeeper.application.DTOs.*;
import com.example.innkeeper.domain.entities.EnumStatusUsuario;
import com.example.innkeeper.domain.entities.Usuario;
import com.example.innkeeper.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    @Value("${spring.secret}")
    private String secret;

    // Injeção via construtor (evita Field Injection e cumpre as boas práticas)
    public UsuarioService(UsuarioRepository usuarioRepository, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }

    // --- AUTENTICAÇÃO E REGRAS ESPECÍFICAS DE ADMIN / DTOs ---

    public LoginResponse validarUsuarioAutenticadoERetornaToken(LoginRequest loginRequest) {
        if (usuarioRepository.existsUsuarioByEmailAndSenha(loginRequest.email(), loginRequest.senha())) {
            var token = tokenService.gerarToken(loginRequest.email());
            return new LoginResponse(token);
        }
        return null;
    }

    public List<UsuarioResponse> ListarTodosUsuariosTable() {
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

    // --- MÉTODOS CRUD BÁSICOS (Utilizados pela UsuarioController) ---

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public boolean atualizarStatus(Long id, EnumStatusUsuario novoStatus) {
        Optional<Usuario> usuarioBanco = usuarioRepository.findById(id);
        if (usuarioBanco.isPresent()) {
            Usuario usuario = usuarioBanco.get();
            usuario.setStatus(novoStatus);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public Usuario atualizar(Long id, Usuario dadosAtualizados) {
        Optional<Usuario> usuarioBanco = usuarioRepository.findById(id);
        if (usuarioBanco.isPresent()) {
            Usuario usuario = usuarioBanco.get();
            usuario.setStatus(dadosAtualizados.getStatus());
            usuario.setNome(dadosAtualizados.getNome());
            usuario.setSenha(dadosAtualizados.getSenha());
            usuario.setCpf(dadosAtualizados.getCpf());
            usuario.setEmail(dadosAtualizados.getEmail());
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    public boolean excluirLogico(Long id) {
        Optional<Usuario> usuarioBanco = usuarioRepository.findById(id);
        if (usuarioBanco.isPresent()) {
            Usuario usuario = usuarioBanco.get();
            usuario.setStatus(EnumStatusUsuario.EXCLUIDO);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }
}