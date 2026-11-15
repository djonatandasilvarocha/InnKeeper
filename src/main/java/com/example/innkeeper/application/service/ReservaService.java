package com.example.innkeeper.application.service;

import com.example.innkeeper.application.DTOs.*;
import com.example.innkeeper.domain.entities.*;
import com.example.innkeeper.domain.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final HospedeRepository hospedeRepository;
    private final QuartoRepository quartoRepository;
    private final UsuarioRepository usuarioRepository;

    public ReservaService(
            ReservaRepository reservaRepository,
            HospedeRepository hospedeRepository,
            QuartoRepository quartoRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.reservaRepository = reservaRepository;
        this.hospedeRepository = hospedeRepository;
        this.quartoRepository = quartoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // ==========================================
    // USUÁRIO AUTENTICADO
    // ==========================================

    private Usuario buscarUsuarioAutenticado() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof UsuarioLogado usuarioLogado)) {
            throw new IllegalStateException("Usuário não autenticado.");
        }

        return usuarioRepository.findById(usuarioLogado.id())
                .orElseThrow(() ->
                        new IllegalArgumentException("Usuário não encontrado."));
    }

    // ==========================================
    // VALIDAÇÃO DE HÓSPEDE E QUARTO
    // ==========================================

    private Hospede buscarHospedeDoUsuario(Long hospedeId, Usuario usuario) {

        if (hospedeId == null) {
            throw new IllegalArgumentException("Informe o hóspede da reserva.");
        }

        Hospede hospede = hospedeRepository
                .findByIdAndUsuario_IdAndStatusNot(
                        hospedeId,
                        usuario.getId(),
                        EnumStatusHospede.EXCLUIDO
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Hóspede não encontrado ou não pertence ao usuário autenticado."
                        ));

        if (hospede.getStatus() != EnumStatusHospede.ATIVO) {
            throw new IllegalArgumentException(
                    "Não é possível realizar uma reserva para um hóspede que não está ativo."
            );
        }

        return hospede;
    }

    private Quarto buscarQuartoDoUsuario(Long quartoId, Usuario usuario) {

        if (quartoId == null) {
            throw new IllegalArgumentException("Informe o quarto da reserva.");
        }

        Quarto quarto = quartoRepository
                .findByIdAndUsuario_IdAndStatusNot(
                        quartoId,
                        usuario.getId(),
                        EnumStatusQuarto.EXCLUIDO
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Quarto não encontrado ou não pertence ao usuário autenticado."
                        ));

        if (quarto.getStatus() != EnumStatusQuarto.DISPONIVEL) {
            throw new IllegalArgumentException(
                    "O quarto não está disponível para reserva."
            );
        }

        return quarto;
    }

    // ==========================================
    // LISTAR RESERVAS
    // ==========================================

    public List<ReservaResponse> listarTodasDto() {

        Usuario usuario = buscarUsuarioAutenticado();

        return reservaRepository
                .findByUsuario_IdAndStatusNot(
                        usuario.getId(),
                        EnumStatusReserva.EXCLUIDA
                )
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    // ==========================================
    // BUSCAR RESERVA POR ID
    // ==========================================

    public Optional<ReservaResponse> buscarPorIdDto(Long id) {

        Usuario usuario = buscarUsuarioAutenticado();

        return reservaRepository
                .findByIdAndUsuario_IdAndStatusNot(
                        id,
                        usuario.getId(),
                        EnumStatusReserva.EXCLUIDA
                )
                .map(this::converterParaResponse);
    }

    public boolean existePorId(Long id) {

        return buscarPorIdDto(id).isPresent();
    }

    // ==========================================
    // CONVERTER PARA DTO
    // ==========================================

    private ReservaResponse converterParaResponse(Reserva reserva) {

        return new ReservaResponse(
                reserva.getId(),
                reserva.getDataEntrada(),
                reserva.getDataSaida(),
                reserva.getValorTotal(),
                reserva.getStatus().toString(),
                reserva.getHospede().getId(),
                reserva.getQuarto().getId()
        );
    }

    // ==========================================
    // VALIDAR DATAS
    // ==========================================

    private void validarDatas(LocalDate entrada, LocalDate saida) {

        if (entrada == null || saida == null) {
            throw new IllegalArgumentException(
                    "As datas de entrada e saída são obrigatórias."
            );
        }

        if (!entrada.isBefore(saida)) {
            throw new IllegalArgumentException(
                    "A data de entrada deve ser anterior à data de saída."
            );
        }
    }

    // ==========================================
    // CALCULAR VALOR TOTAL
    // ==========================================

    private BigDecimal calcularValorTotal(
            LocalDate entrada,
            LocalDate saida,
            Quarto quarto
    ) {

        long quantidadeDias = ChronoUnit.DAYS.between(entrada, saida);

        return quarto.getDiaria().multiply(
                BigDecimal.valueOf(quantidadeDias)
        );
    }

    // ==========================================
    // VERIFICAR CONFLITO DE RESERVAS
    // ==========================================

    private void verificarConflito(
            Long quartoId,
            Long reservaId,
            LocalDate entrada,
            LocalDate saida
    ) {

        List<EnumStatusReserva> statusAtivos = List.of(
                EnumStatusReserva.PENDENTE,
                EnumStatusReserva.CONFIRMADA
        );

        boolean existeConflito;

        if (reservaId == null) {

            existeConflito = reservaRepository
                    .existsByQuarto_IdAndStatusInAndDataEntradaLessThanAndDataSaidaGreaterThan(
                            quartoId,
                            statusAtivos,
                            saida,
                            entrada
                    );

        } else {

            existeConflito = reservaRepository
                    .existsByQuarto_IdAndIdNotAndStatusInAndDataEntradaLessThanAndDataSaidaGreaterThan(
                            quartoId,
                            reservaId,
                            statusAtivos,
                            saida,
                            entrada
                    );
        }

        if (existeConflito) {
            throw new IllegalArgumentException(
                    "O quarto já possui uma reserva para o período informado."
            );
        }
    }

    // ==========================================
    // CRIAR RESERVA
    // ==========================================

    public ReservaResponse criar(CriarReservaRequest request) {

        Usuario usuario = buscarUsuarioAutenticado();

        validarDatas(request.dataEntrada(), request.dataSaida());

        Hospede hospede = buscarHospedeDoUsuario(
                request.hospedeId(),
                usuario
        );

        Quarto quarto = buscarQuartoDoUsuario(
                request.quartoId(),
                usuario
        );

        verificarConflito(
                quarto.getId(),
                null,
                request.dataEntrada(),
                request.dataSaida()
        );

        Reserva reserva = new Reserva();

        reserva.setDataEntrada(request.dataEntrada());
        reserva.setDataSaida(request.dataSaida());
        reserva.setHospede(hospede);
        reserva.setQuarto(quarto);
        reserva.setUsuario(usuario);
        reserva.setStatus(EnumStatusReserva.PENDENTE);

        reserva.setValorTotal(
                calcularValorTotal(
                        request.dataEntrada(),
                        request.dataSaida(),
                        quarto
                )
        );

        return converterParaResponse(
                reservaRepository.save(reserva)
        );
    }

    // ==========================================
    // ATUALIZAR RESERVA COMPLETA
    // ==========================================

    public ReservaResponse atualizarDto(
            Long id,
            AtualizarReservaRequest request
    ) {

        Usuario usuario = buscarUsuarioAutenticado();

        Reserva reserva = reservaRepository
                .findByIdAndUsuario_IdAndStatusNot(
                        id,
                        usuario.getId(),
                        EnumStatusReserva.EXCLUIDA
                )
                .orElseThrow(() ->
                        new IllegalArgumentException("Reserva não encontrada."));

        validarAtualizacaoReserva(reserva, request.status());

        validarDatas(request.dataEntrada(), request.dataSaida());

        Hospede hospede = reserva.getHospede();
        Quarto quarto = reserva.getQuarto();

        if (request.hospedeId() != null) {
            hospede = buscarHospedeDoUsuario(
                    request.hospedeId(),
                    usuario
            );
        }

        if (request.quartoId() != null) {
            quarto = buscarQuartoDoUsuario(
                    request.quartoId(),
                    usuario
            );
        }

        verificarConflito(
                quarto.getId(),
                reserva.getId(),
                request.dataEntrada(),
                request.dataSaida()
        );

        reserva.setDataEntrada(request.dataEntrada());
        reserva.setDataSaida(request.dataSaida());
        reserva.setHospede(hospede);
        reserva.setQuarto(quarto);

        if (request.status() != null) {
            reserva.setStatus(request.status());
        }

        reserva.setValorTotal(
                calcularValorTotal(
                        reserva.getDataEntrada(),
                        reserva.getDataSaida(),
                        quarto
                )
        );

        return converterParaResponse(
                reservaRepository.save(reserva)
        );
    }

    // ==========================================
    // VALIDAR ATUALIZAÇÃO
    // ==========================================

    private void validarAtualizacaoReserva(
            Reserva reserva,
            EnumStatusReserva novoStatus
    ) {

        if (reserva.getStatus() == EnumStatusReserva.FINALIZADA ||
                reserva.getStatus() == EnumStatusReserva.CANCELADA) {

            throw new IllegalArgumentException(
                    "Não é possível alterar uma reserva finalizada ou cancelada."
            );
        }

        if (novoStatus == EnumStatusReserva.FINALIZADA) {
            throw new IllegalArgumentException(
                    "Para finalizar uma reserva, utilize o endpoint de checkout."
            );
        }

        if (novoStatus == EnumStatusReserva.EXCLUIDA) {
            throw new IllegalArgumentException(
                    "Para excluir uma reserva, utilize o endpoint DELETE."
            );
        }
    }

    // ==========================================
    // ATUALIZAÇÃO PARCIAL
    // ==========================================

    public ReservaResponse atualizarParcialDto(
            Long id,
            AtualizarReservaRequest request
    ) {

        Usuario usuario = buscarUsuarioAutenticado();

        Reserva reserva = reservaRepository
                .findByIdAndUsuario_IdAndStatusNot(
                        id,
                        usuario.getId(),
                        EnumStatusReserva.EXCLUIDA
                )
                .orElseThrow(() ->
                        new IllegalArgumentException("Reserva não encontrada."));

        validarAtualizacaoReserva(reserva, request.status());

        Hospede hospede = reserva.getHospede();
        Quarto quarto = reserva.getQuarto();

        if (request.hospedeId() != null) {
            hospede = buscarHospedeDoUsuario(
                    request.hospedeId(),
                    usuario
            );
        }

        if (request.quartoId() != null) {
            quarto = buscarQuartoDoUsuario(
                    request.quartoId(),
                    usuario
            );
        }

        LocalDate entrada = request.dataEntrada() != null
                ? request.dataEntrada()
                : reserva.getDataEntrada();

        LocalDate saida = request.dataSaida() != null
                ? request.dataSaida()
                : reserva.getDataSaida();

        validarDatas(entrada, saida);

        verificarConflito(
                quarto.getId(),
                reserva.getId(),
                entrada,
                saida
        );

        reserva.setDataEntrada(entrada);
        reserva.setDataSaida(saida);
        reserva.setHospede(hospede);
        reserva.setQuarto(quarto);

        if (request.status() != null) {
            reserva.setStatus(request.status());
        }

        reserva.setValorTotal(
                calcularValorTotal(entrada, saida, quarto)
        );

        return converterParaResponse(
                reservaRepository.save(reserva)
        );
    }

    // ==========================================
    // CHECKOUT
    // ==========================================

    public ReservaResponse realizarCheckoutDto(Long id) {

        Usuario usuario = buscarUsuarioAutenticado();

        Reserva reserva = reservaRepository
                .findByIdAndUsuario_IdAndStatusNot(
                        id,
                        usuario.getId(),
                        EnumStatusReserva.EXCLUIDA
                )
                .orElseThrow(() ->
                        new IllegalArgumentException("Reserva não encontrada."));

        if (reserva.getStatus() == EnumStatusReserva.FINALIZADA ||
                reserva.getStatus() == EnumStatusReserva.CANCELADA) {

            throw new IllegalArgumentException(
                    "Esta reserva já foi finalizada ou cancelada."
            );
        }

        validarDatas(
                reserva.getDataEntrada(),
                reserva.getDataSaida()
        );

        if (LocalDate.now().isBefore(reserva.getDataSaida())) {
            throw new IllegalArgumentException(
                    "O checkout só pode ser realizado na data de saída ou depois dela."
            );
        }

        Quarto quarto = reserva.getQuarto();

        reserva.setValorTotal(
                calcularValorTotal(
                        reserva.getDataEntrada(),
                        reserva.getDataSaida(),
                        quarto
                )
        );

        reserva.setStatus(EnumStatusReserva.FINALIZADA);

        LocalDate hoje = LocalDate.now();

        boolean outraReservaEmAndamento = reservaRepository
                .existsByQuarto_IdAndIdNotAndStatusInAndDataEntradaLessThanEqualAndDataSaidaGreaterThan(
                        quarto.getId(),
                        reserva.getId(),
                        List.of(
                                EnumStatusReserva.PENDENTE,
                                EnumStatusReserva.CONFIRMADA
                        ),
                        hoje,
                        hoje
                );

        if (!outraReservaEmAndamento) {
            quarto.setStatus(EnumStatusQuarto.DISPONIVEL);
            quartoRepository.save(quarto);
        }

        return converterParaResponse(
                reservaRepository.save(reserva)
        );
    }

    // ==========================================
    // EXCLUSÃO LÓGICA
    // ==========================================

    public void deletarLogicoOuFisico(Long id) {

        Usuario usuario = buscarUsuarioAutenticado();

        Reserva reserva = reservaRepository
                .findByIdAndUsuario_IdAndStatusNot(
                        id,
                        usuario.getId(),
                        EnumStatusReserva.EXCLUIDA
                )
                .orElseThrow(() ->
                        new IllegalArgumentException("Reserva não encontrada."));

        reserva.setStatus(EnumStatusReserva.EXCLUIDA);

        reservaRepository.save(reserva);
    }
}