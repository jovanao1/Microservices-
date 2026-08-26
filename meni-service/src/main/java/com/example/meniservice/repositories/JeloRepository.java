package com.example.meniservice.repositories;

import com.example.meniservice.model.Jelo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JeloRepository extends JpaRepository<Jelo, Integer> {
    Jelo findJeloByNazivJelo(String nazivJela);

    List<Jelo> findByKategorijajelaIdkategorijajelaNazivKategorijaJela(String kategorija);
}
