
package com.example.innkeeper.domain.repository;

import com.example.innkeeper.domain.entities.Hospede;
import com.example.innkeeper.domain.entities.EnumStatusHospede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospedeRepository extends JpaRepository<Hospede, Long> {

    // Buscar hóspedes de um usuário específico
    List<Hospede> findByUsuario_IdAndStatusNot(
            Long usuarioId,
            EnumStatusHospede status
    );

    // Buscar um hóspede específico pertencente a um usuário
    Optional<Hospede> findByIdAndUsuario_IdAndStatusNot(
            Long id,
            Long usuarioId,
            EnumStatusHospede status
    );
}
