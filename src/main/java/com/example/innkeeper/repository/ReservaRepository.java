package com.example.innkeeper.repository;

import com.example.innkeeper.entities.Hospede;
import com.example.innkeeper.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
