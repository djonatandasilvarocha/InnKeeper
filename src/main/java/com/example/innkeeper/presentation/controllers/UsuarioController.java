package com.example.innkeeper.presentation.controllers;

import com.example.innkeeper.application.DTOs.*;
import com.example.innkeeper.application.service.UsuarioService;
import com.example.innkeeper.domain.entities.EnumStatusUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Grupo de APIs responsável por controlar a estrutura, criação e consulta de usuários do sistema!")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Método de consulta de lista de usuários")
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodosUsuariosTable());
    }

    @PostMapping("/admin")
    @Operation(summary = "Criar usuário administrador")
    public ResponseEntity<CriarAdminResponse> criarAdmin(@RequestBody CriarAdminRequest criarAdminRequest){
        try {
            CriarAdminResponse respostaSalvar = usuarioService.criarAdmin(criarAdminRequest);
            return ResponseEntity.ok(respostaSalvar);
        } catch(RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Método de consulta de usuário por ID")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        UsuarioResponse usuarioResponse = usuarioService.buscarPorId(id);
        if (usuarioResponse != null) {
            return ResponseEntity.ok(usuarioResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de usuários")
    public ResponseEntity<?> criar(@RequestBody CriarUsuarioRequest request) {
        try {
            // Utiliza o método correspondente do service recebendo o CriarUsuarioRequest
            UsuarioResponse novoUsuario = usuarioService.salvarUsuario(request, 0L);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do usuário")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest statusRequest) {
        boolean atualizado = usuarioService.atualizarStatus(statusRequest.status(), id);
        if (atualizado) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário completo")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody CriarUsuarioRequest request) {
        try {
            UsuarioResponse usuarioAtualizado = usuarioService.salvarUsuario(request, id);
            if (usuarioAtualizado != null) {
                return ResponseEntity.ok(usuarioAtualizado);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Exclusão lógica de usuário")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        boolean excluido = usuarioService.atualizarStatus(EnumStatusUsuario.EXCLUIDO, id);
        if (excluido) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}