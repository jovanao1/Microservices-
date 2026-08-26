package com.example.rezervacijaservice.repositories;

import com.example.rezervacijaservice.model.Korisnik;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KorisnikRepository extends JpaRepository<Korisnik,Integer> {
    Korisnik findByUsernamekorisnik(String username);

    Korisnik findByPasswordkorisnik(String passwordkorisnik);

    boolean existsKorisnikByUsernamekorisnikAndPasswordkorisnik(String username, String password);
}
