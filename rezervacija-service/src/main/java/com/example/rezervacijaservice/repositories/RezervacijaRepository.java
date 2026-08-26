package com.example.rezervacijaservice.repositories;
import com.example.rezervacijaservice.model.Korisnik;
import com.example.rezervacijaservice.model.Rezervacija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface RezervacijaRepository extends JpaRepository<Rezervacija, Integer> {
    @Query(value = """
        SELECT 
            MONTH(datumvremeRezervacija) AS mesec,
            COUNT(*) AS broj
        FROM rezervacija
        WHERE YEAR(datumvremeRezervacija) = 2026
        GROUP BY MONTH(datumvremeRezervacija)
        ORDER BY mesec
    """, nativeQuery = true)
    List<Object[]> rezervacijePoMesecimaU2026();

    Rezervacija findByKorisnikIdkorisnikAndDatumvremeRezervacija(Korisnik kor, LocalDateTime datum);
}
