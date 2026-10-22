package com.example.innkeeper.presentation.controllers;

import com.example.innkeeper.application.DTOs.AtualizarStatusRequest;
import com.example.innkeeper.application.DTOs.AtualizarUsuarioRequest; // Crie este DTO na camada application se não existir
import com.example.innkeeper.application.DTOs.CriarAdminRequest;
import com.example.innkeeper.application.DTOs.CriarAdminResponse;
import com.example.innkeeper.application.DTOs.CriarUsuarioRequest; // Crie este DTO na camada application se não existir
import com.example.innkeeper.application.DTOs.LoginRequest;
import com.example.innkeeper.application.DTOs.LoginResponse;
import com.example.innkeeper.application.DTOs.UsuarioResponse;
import com.example.innkeeper.application.service.UsuarioService;
// ADDEUS: O import de com.example.innkeeper.domain.entities.Usuario foi removido daqui![cite: 1, 2, 3]

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
    @Operation(summary = "Método de consulta de lista de usuários", description = "Método responsável por efetuar a consulta de todos os usuários sem filtro.")
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
    @Operation(summary = "Método de consulta de usuário por ID", description = "Método responsável por efetuar a busca de um usuário específico através do seu ID.")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        UsuarioResponse usuarioResponse = usuarioService.buscarPorIdDto(id); // Ajuste no service para retornar DTO
        if (usuarioResponse != null) {
            return ResponseEntity.ok(usuarioResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de usuários", description = "Método responsável por efetuar a criação de novos usuários.")
    public ResponseEntity<UsuarioResponse> criar(@RequestBody CriarUsuarioRequest request) {
        UsuarioResponse novoUsuario = usuarioService.criar(request); // Service recebe o Request DTO e devolve Response DTO
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status do usuário", description = "Método responsável por atualizar apenas o status de um usuário específico.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest statusRequest) {
        boolean atualizado = usuarioService.atualizarStatus(id, statusRequest.status());
        if (atualizado) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualização de usuário", description = "Método responsável por atualizar todos os dados de um usuário existente.")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long id, @RequestBody AtualizarUsuarioRequest request) {
        try {
            UsuarioResponse usuarioAtualizado = usuarioService.atualizarDto(id, request);
            if (usuarioAtualizado != null) {
                return ResponseEntity.ok(usuarioAtualizado);
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de exclusão de usuário", description = "Método responsável por efetuar a exclusão lógica de um usuário, alterando seu status para EXCLUIDO.")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        boolean excluido = usuarioService.excluirLogico(id);
        if (excluido) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}