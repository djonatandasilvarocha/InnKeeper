
package com.example.innkeeper.application.service;

import com.example.innkeeper.application.DTOs.AtualizarQuartoRequest;
import com.example.innkeeper.application.DTOs.CriarQuartoRequest;
import com.example.innkeeper.application.DTOs.QuartoResponse;
import com.example.innkeeper.application.DTOs.UsuarioLogado;
import com.example.innkeeper.domain.entities.EnumStatusQuarto;
import com.example.innkeeper.domain.entities.Quarto;
import com.example.innkeeper.domain.entities.Usuario;
import com.example.innkeeper.domain.repository.QuartoRepository;
import com.example.innkeeper.domain.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuartoService {

    private final QuartoRepository quartoRepository;
    private final UsuarioRepository usuarioRepository;

    public QuartoService(
            QuartoRepository quartoRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.quartoRepository = quartoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Identificar o usuário autenticado
    private Usuario buscarUsuarioAutenticado() {

        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (!(principal instanceof UsuarioLogado usuarioLogado)) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        return usuarioRepository.findById(usuarioLogado.id())
                .orElseThrow(() -> new RuntimeException(
                        "Usuário não encontrado."
                ));
    }

    // Listar quartos do usuário autenticado
    public List<QuartoResponse> listarTodosDto() {

        Usuario usuario = buscarUsuarioAutenticado();

        return quartoRepository
                .findByUsuario_IdAndStatusNot(
                        usuario.getId(),
                        EnumStatusQuarto.EXCLUIDO
                )
                .stream()
                .map(QuartoResponse::new)
                .toList();
    }

    // Buscar quarto pelo ID
    public QuartoResponse buscarPorIdDto(Long id) {

        Usuario usuario = buscarUsuarioAutenticado();

        return quartoRepository
                .findByIdAndUsuario_IdAndStatusNot(
                        id,
                        usuario.getId(),
                        EnumStatusQuarto.EXCLUIDO
                )
                .map(QuartoResponse::new)
                .orElse(null);
    }

    // Verificar se o quarto pertence ao usuário
    public boolean existePorId(Long id) {

        Usuario usuario = buscarUsuarioAutenticado();

        return quartoRepository
                .findByIdAndUsuario_IdAndStatusNot(
                        id,
                        usuario.getId(),
                        EnumStatusQuarto.EXCLUIDO
                )
                .isPresent();
    }

    // Cadastrar quarto
    public QuartoResponse criar(CriarQuartoRequest request) {

        Usuario usuario = buscarUsuarioAutenticado();

        Quarto quarto = new Quarto();

        quarto.setNumero(request.numero());
        quarto.setTipo(request.tipo());
        quarto.setCapacidade(request.capacidade());
        quarto.setDiaria(request.diaria());
        quarto.setStatus(EnumStatusQuarto.DISPONIVEL);
        quarto.setUsuario(usuario);

        Quarto salvo = quartoRepository.save(quarto);

        return new QuartoResponse(salvo);
    }

    // Atualizar quarto
    public QuartoResponse atualizarDto(
            Long id,
            AtualizarQuartoRequest request
    ) {

        Usuario usuario = buscarUsuarioAutenticado();

        Optional<Quarto> quartoBanco =
                quartoRepository.findByIdAndUsuario_IdAndStatusNot(
                        id,
                        usuario.getId(),
                        EnumStatusQuarto.EXCLUIDO
                );

        if (quartoBanco.isPresent()) {

            Quarto quarto = quartoBanco.get();

            quarto.setNumero(request.numero());
            quarto.setTipo(request.tipo());
            quarto.setCapacidade(request.capacidade());
            quarto.setDiaria(request.diaria());

            if (request.status() != null) {
                quarto.setStatus(request.status());
            }

            Quarto atualizado = quartoRepository.save(quarto);

            return new QuartoResponse(atualizado);
        }

        return null;
    }

    // Atualizar quarto parcialmente
    public QuartoResponse atualizarParcialDto(
            Long id,
            AtualizarQuartoRequest request
    ) {

        Usuario usuario = buscarUsuarioAutenticado();

        Optional<Quarto> quartoBanco =
                quartoRepository.findByIdAndUsuario_IdAndStatusNot(
                        id,
                        usuario.getId(),
                        EnumStatusQuarto.EXCLUIDO
                );

        if (quartoBanco.isPresent()) {

            Quarto quarto = quartoBanco.get();

            if (request.numero() != null) {
                quarto.setNumero(request.numero());
            }

            if (request.tipo() != null) {
                quarto.setTipo(request.tipo());
            }

            if (request.capacidade() != null) {
                quarto.setCapacidade(request.capacidade());
            }

            if (request.diaria() != null) {
                quarto.setDiaria(request.diaria());
            }

            if (request.status() != null) {
                quarto.setStatus(request.status());
            }

            Quarto atualizado = quartoRepository.save(quarto);

            return new QuartoResponse(atualizado);
        }

        return null;
    }

    // Excluir quarto logicamente
    public boolean deletarPorId(Long id) {

        Usuario usuario = buscarUsuarioAutenticado();

        Optional<Quarto> quartoBanco =
                quartoRepository.findByIdAndUsuario_IdAndStatusNot(
                        id,
                        usuario.getId(),
                        EnumStatusQuarto.EXCLUIDO
                );

        if (quartoBanco.isPresent()) {

            Quarto quarto = quartoBanco.get();

            quarto.setStatus(EnumStatusQuarto.EXCLUIDO);

            quartoRepository.save(quarto);

            return true;
        }

        return false;
    }
}
