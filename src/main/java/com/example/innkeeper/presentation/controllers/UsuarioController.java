package com.example.innkeeper.presentation.controllers;

import com.example.innkeeper.application.DTOs.AtualizarStatusRequest;
import com.example.innkeeper.application.DTOs.CriarAdminRequest;
import com.example.innkeeper.application.DTOs.CriarAdminResponse;
import com.example.innkeeper.application.DTOs.LoginRequest;
import com.example.innkeeper.application.DTOs.LoginResponse;
import com.example.innkeeper.application.DTOs.UsuarioResponse;
import com.example.innkeeper.application.service.UsuarioService;
import com.example.innkeeper.domain.entities.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Grupo de APIs responsavel por controlar a estrutura e criação e consulta de usuários do sistema!")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Injeção via construtor (Adeus UsuarioRepository na Controller!)
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário", description = "Valida as credenciais de email e senha e retorna um token de acesso.")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = usuarioService.validarUsuarioAutenticadoERetornaToken(loginRequest);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping
    @Operation(summary = "Metodo de consulta de lista de usuários!", description = "Metodo responsavel em efetuar a consulta de todos os usuarios sem filtro!")
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        return ResponseEntity.ok(usuarioService.ListarTodosUsuariosTable());
    }

    @PostMapping("/admin")
    public ResponseEntity<CriarAdminResponse> criarAdmin(@RequestBody CriarAdminRequest criarAdminRequest){
        try {
            CriarAdminResponse respostaSalvar = usuarioService.criarAdmin(criarAdminRequest);
            return ResponseEntity.ok(respostaSalvar);
        } catch(RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Metodo de consulta de usuario por id", description = "Metodo responsavel em efetuar a busca de um usuario especifico atraves do seu id")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> usuarioBanco = usuarioService.buscarPorId(id);
        return usuarioBanco.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Metodo de criação de usuários!",
            description = "Metodo responsavel em efetuar a criação de novos usuarios!")
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        Usuario usuarioBanco = usuarioService.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioBanco);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Metodo de atualização de status do usuario", description = "Metodo responsavel em atualizar apenas o status de um usuario especifico")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest statusRequest) {
        boolean atualizado = usuarioService.atualizarStatus(id, statusRequest.status());
        if (atualizado) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Metodo de atualização de usuario", description = "Metodo responsavel em atualizar todos os dados de um usuario existente")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizar(id, usuario);
            if (usuarioAtualizado != null) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Metodo de exclusão de usuario", description = "Metodo responsavel em efetuar a exclusão logica de um usuario, alterando seu status para EXCLUIDO")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        boolean excluido = usuarioService.excluirLogico(id);
        if (excluido) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}