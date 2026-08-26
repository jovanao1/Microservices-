package com.example.meniservice.repositories;

import com.example.meniservice.model.Kategorijajela;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KategorijajelaRepository extends JpaRepository<Kategorijajela, Integer> {
    Kategorijajela findByNazivKategorijaJela(String nazivKategorijaJela);
}
