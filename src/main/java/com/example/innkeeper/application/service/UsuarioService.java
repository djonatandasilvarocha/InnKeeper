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

    public UsuarioService(UsuarioRepository usuarioRepository, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }

    // --- AUTENTICAÇÃO E REGRAS ESPECÍFICAS DE ADMIN / DTOs ---

    public boolean autenticar(String email, String senha) {
        return usuarioRepository.existsUsuarioByEmailAndSenha(email, senha);
    }

    public LoginResponse validarUsuarioAutenticadoERetornaToken(LoginRequest loginRequest) {
        if (autenticar(loginRequest.email(), loginRequest.senha())) {
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

    // --- NOVOS MÉTODOS CRUD USANDO DTOs (Para a UsuarioController) ---

    public UsuarioResponse buscarPorIdDto(Long id) {
        Optional<Usuario> usuarioBanco = usuarioRepository.findById(id);
        return usuarioBanco.map(UsuarioResponse::new).orElse(null);
    }

    public UsuarioResponse criar(CriarUsuarioRequest request) {
        // Converte o DTO de Request em Entidade de Domínio
        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(request.senha());
        usuario.setCpf(request.cpf());
        usuario.setStatus(EnumStatusUsuario.ATIVO); // Definindo um status padrão inicial, se aplicável

        Usuario salvo = usuarioRepository.save(usuario);

        // Retorna o Response DTO
        return new UsuarioResponse(salvo);
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

    public UsuarioResponse atualizarDto(Long id, AtualizarUsuarioRequest dadosAtualizados) {
        Optional<Usuario> usuarioBanco = usuarioRepository.findById(id);
        if (usuarioBanco.isPresent()) {
            Usuario usuario = usuarioBanco.get();

            // Atualiza os campos permitidos com base no Request DTO
            usuario.setNome(dadosAtualizados.nome());
            usuario.setEmail(dadosAtualizados.email());
            usuario.setSenha(dadosAtualizados.senha());
            usuario.setCpf(dadosAtualizados.cpf());

            Usuario atualizado = usuarioRepository.save(usuario);
            return new UsuarioResponse(atualizado);
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