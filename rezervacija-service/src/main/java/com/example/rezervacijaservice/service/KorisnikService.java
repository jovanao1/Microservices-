package com.example.rezervacijaservice.service;

import com.example.rezervacijaservice.exception.EntityAlreadyExists;
import com.example.rezervacijaservice.model.Korisnik;
import com.example.rezervacijaservice.repositories.KorisnikRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KorisnikService {
    private final KorisnikRepository korisnikRepository;

    public Korisnik dodajKorisnika(Korisnik korisnik){
        if(korisnikRepository.findByUsernamekorisnik(korisnik.getUsernamekorisnik()) != null){
            throw new EntityAlreadyExists("Korisnik sa username " + korisnik.getUsernamekorisnik() + " vec postoji." +
                    "Odaberite novi username.");
        }else if(korisnikRepository.findByPasswordkorisnik(korisnik.getPasswordkorisnik()) != null){
            throw new EntityAlreadyExists("Lozinka je zauzeta." +
                    "Odaberite novu lozinku.");
        }
        return  korisnikRepository.save(korisnik);
    }

    public boolean postojiKorisnik(String username, String password){
        return korisnikRepository.existsKorisnikByUsernamekorisnikAndPasswordkorisnik(username, password);
    }
}
