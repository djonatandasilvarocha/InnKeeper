package com.example.innkeeper.domain.repository;

import com.example.innkeeper.domain.entities.Hospede;
import com.example.innkeeper.domain.entities.EnumStatusHospede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospedeRepository extends JpaRepository<Hospede, Long> {

    List<Hospede> findByStatusNot(EnumStatusHospede status);

    Optional<Hospede> findByIdAndStatusNot(Long id, EnumStatusHospede status);
}