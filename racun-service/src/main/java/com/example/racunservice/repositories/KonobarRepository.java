package com.example.racunservice.repositories;

import com.example.racunservice.model.Konobar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KonobarRepository extends JpaRepository<Konobar,Integer> {
    Konobar findByImeKonobar(String imeKonobar);

    boolean existsByIdAndImeKonobar(Integer id, String ime);
}
