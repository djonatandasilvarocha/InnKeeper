
package com.example.innkeeper.application.service;

import com.example.innkeeper.application.DTOs.AtualizarHospedeRequest;
import com.example.innkeeper.application.DTOs.CriarHospedeRequest;
import com.example.innkeeper.application.DTOs.HospedeResponse;
import com.example.innkeeper.application.DTOs.UsuarioLogado;
import com.example.innkeeper.domain.entities.EnumStatusHospede;
import com.example.innkeeper.domain.entities.Hospede;
import com.example.innkeeper.domain.entities.Usuario;
import com.example.innkeeper.domain.repository.HospedeRepository;
import com.example.innkeeper.domain.repository.UsuarioRepository;
import com.example.innkeeper.domain.valueobjects.Celular;
import com.example.innkeeper.domain.valueobjects.Cpf;
import com.example.innkeeper.domain.valueobjects.Email;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HospedeService {

    private final HospedeRepository hospedeRepository;
    private final UsuarioRepository usuarioRepository;

    public HospedeService(
            HospedeRepository hospedeRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.hospedeRepository = hospedeRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // IDENTIFICAR USUÁRIO AUTENTICADO

    private Usuario buscarUsuarioAutenticado() {

        var authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof UsuarioLogado usuarioLogado)) {
            throw new IllegalStateException("Usuário não autenticado.");
        }

        return usuarioRepository.findById(usuarioLogado.id())
                .orElseThrow(() ->
                        new IllegalStateException("Usuário não encontrado.")
                );
    }

    // MÉTODOS DE LEITURA

    public List<HospedeResponse> listarTodosDto() {

        Usuario usuario = buscarUsuarioAutenticado();

        return hospedeRepository
                .findByUsuario_IdAndStatusNot(
                        usuario.getId(),
                        EnumStatusHospede.EXCLUIDO
                )
                .stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    public HospedeResponse buscarPorIdDto(Long id) {

        Usuario usuario = buscarUsuarioAutenticado();

        return hospedeRepository
                .findByIdAndUsuario_IdAndStatusNot(
                        id,
                        usuario.getId(),
                        EnumStatusHospede.EXCLUIDO
                )
                .map(this::converterParaResponse)
                .orElse(null);
    }

    public boolean existePorId(Long id) {

        Usuario usuario = buscarUsuarioAutenticado();

        return hospedeRepository
                .findByIdAndUsuario_IdAndStatusNot(
                        id,
                        usuario.getId(),
                        EnumStatusHospede.EXCLUIDO
                )
                .isPresent();
    }

    // MÉTODO DE CRIAÇÃO

    public HospedeResponse criar(CriarHospedeRequest request) {

        Usuario usuario = buscarUsuarioAutenticado();

        if (request.cpf() == null || request.email() == null) {
            throw new IllegalArgumentException(
                    "CPF e e-mail são obrigatórios."
            );
        }

        Hospede hospede = new Hospede();

        hospede.setNome(request.nome());
        hospede.setCpf(new Cpf(request.cpf()));
        hospede.setEmail(new Email(request.email()));

        if (request.telefone() != null) {
            hospede.setTelefone(new Celular(request.telefone()));
        }

        hospede.setStatus(EnumStatusHospede.ATIVO);

        // Associar hóspede ao usuário autenticado
        hospede.setUsuario(usuario);

        Hospede salvo = hospedeRepository.save(hospede);

        return converterParaResponse(salvo);
    }

    // MÉTODO DE ATUALIZAÇÃO COMPLETA

    public HospedeResponse atualizarDto(
            Long id,
            AtualizarHospedeRequest request
    ) {

        Usuario usuario = buscarUsuarioAutenticado();

        Optional<Hospede> hospedeBanco =
                hospedeRepository.findByIdAndUsuario_IdAndStatusNot(
                        id,
                        usuario.getId(),
                        EnumStatusHospede.EXCLUIDO
                );

        if (hospedeBanco.isPresent()) {

            Hospede hospede = hospedeBanco.get();

            if (request.nome() == null ||
                    request.cpf() == null ||
                    request.email() == null) {

                throw new IllegalArgumentException(
                        "Nome, CPF e e-mail são obrigatórios."
                );
            }

            hospede.setNome(request.nome());
            hospede.setCpf(new Cpf(request.cpf()));
            hospede.setEmail(new Email(request.email()));

            if (request.telefone() != null) {
                hospede.setTelefone(new Celular(request.telefone()));
            }

            if (request.status() != null) {
                hospede.setStatus(request.status());
            }

            Hospede atualizado = hospedeRepository.save(hospede);

            return converterParaResponse(atualizado);
        }

        return null;
    }

    // MÉTODO DE ATUALIZAÇÃO PARCIAL

    public HospedeResponse atualizarParcialDto(
            Long id,
            AtualizarHospedeRequest request
    ) {

        Usuario usuario = buscarUsuarioAutenticado();

        Optional<Hospede> hospedeBanco =
                hospedeRepository.findByIdAndUsuario_IdAndStatusNot(
                        id,
                        usuario.getId(),
                        EnumStatusHospede.EXCLUIDO
                );

        if (hospedeBanco.isPresent()) {

            Hospede hospede = hospedeBanco.get();

            if (request.nome() != null) {
                hospede.setNome(request.nome());
            }

            if (request.cpf() != null) {
                hospede.setCpf(new Cpf(request.cpf()));
            }

            if (request.email() != null) {
                hospede.setEmail(new Email(request.email()));
            }

            if (request.telefone() != null) {
                hospede.setTelefone(new Celular(request.telefone()));
            }

            if (request.status() != null) {
                hospede.setStatus(request.status());
            }

            Hospede atualizado = hospedeRepository.save(hospede);

            return converterParaResponse(atualizado);
        }

        return null;
    }

    // MÉTODO DE EXCLUSÃO LÓGICA

    public boolean deletarPorId(Long id) {

        Usuario usuario = buscarUsuarioAutenticado();

        Optional<Hospede> hospedeBanco =
                hospedeRepository.findByIdAndUsuario_IdAndStatusNot(
                        id,
                        usuario.getId(),
                        EnumStatusHospede.EXCLUIDO
                );

        if (hospedeBanco.isPresent()) {

            Hospede hospede = hospedeBanco.get();

            hospede.setStatus(EnumStatusHospede.EXCLUIDO);

            hospedeRepository.save(hospede);

            return true;
        }

        return false;
    }

    // CONVERSÃO DE ENTIDADE PARA DTO

    private HospedeResponse converterParaResponse(Hospede hospede) {

        return new HospedeResponse(
                hospede.getId(),
                hospede.getNome() != null ? hospede.getNome() : "",
                hospede.getCpf() != null ? hospede.getCpf().value() : "",
                hospede.getEmail() != null ? hospede.getEmail().value() : "",
                hospede.getTelefone() != null ? hospede.getTelefone().value() : "",
                hospede.getStatus()
        );
    }
}
