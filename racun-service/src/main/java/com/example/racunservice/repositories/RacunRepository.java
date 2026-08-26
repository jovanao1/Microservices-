package com.example.racunservice.repositories;

import com.example.racunservice.model.Racun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RacunRepository extends JpaRepository<Racun,Integer> {
    @Query(""" 
        SELECT SUM(nr.ukupnaCenaNarudzbina) FROM Racun r
        JOIN r.rezervacijaracunIdrezervacijaracun rr
        JOIN r.narudzbinaimajelaracunIdnarudzbinaimajelaracun nr
        WHERE DATE(rr.datumvremeRezervacija) = :datum
    """)
    Double prihodZaDatum(@Param("datum") LocalDate datum);

    @Query(value = """
        SELECT DATE(rr.datumvremeRezervacija)
        FROM racun r JOIN rezervacijaracun rr ON r.rezervacijaRacun_idrezervacijaRacun = rr.idrezervacijaRacun
        GROUP BY DATE(rr.datumvremeRezervacija)
        HAVING COUNT(*) = (
            SELECT MAX(cnt) FROM (
                SELECT COUNT(*) AS cnt FROM racun r2 JOIN rezervacijaracun rr2 
                ON r2.rezervacijaRacun_idrezervacijaRacun = rr2.idrezervacijaRacun
                GROUP BY DATE(rr2.datumvremeRezervacija)
            ) t
        )
    """, nativeQuery = true)
    List<LocalDate> daniSaNajviseRacuna();

}
