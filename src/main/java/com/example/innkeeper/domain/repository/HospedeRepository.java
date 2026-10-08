package com.example.innkeeper.domain.repository;

import com.example.innkeeper.domain.entities.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospedeRepository extends JpaRepository<Hospede, Long> {
}
