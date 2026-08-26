package com.example.rezervacijaservice.repositories;

import com.example.rezervacijaservice.model.Sto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StoRepository extends JpaRepository<Sto,Integer> {
    @Query("SELECT s FROM Sto s WHERE s.id NOT IN (SELECT r.stoIdsto.id FROM Rezervacija r WHERE r.datumvremeRezervacija BETWEEN :start AND :kraj)")
    List<Sto> findSlobodniStolovi(@Param("start") LocalDateTime start, @Param("kraj") LocalDateTime kraj);
}
