package com.example.racunservice.service;

import com.example.racunservice.dtos.RacunDTO;
import com.example.racunservice.exception.EntityDoesNotExists;
import com.example.racunservice.model.Konobar;
import com.example.racunservice.model.Narudzbinaracun;
import com.example.racunservice.model.Racun;
import com.example.racunservice.model.Rezervacijaracun;
import com.example.racunservice.repositories.KonobarRepository;
import com.example.racunservice.repositories.NarudzbinaracunRepository;
import com.example.racunservice.repositories.RacunRepository;
import com.example.racunservice.repositories.RezervacijaracunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RacunService {
    private final RacunRepository racunRepository;
    private final KonobarRepository konobarRepository;
    private final RezervacijaracunRepository rezervacijaracunRepository;
    private final NarudzbinaracunRepository narudzbinaracunRepository;

    public Racun dodajRacun(Integer idKonobar, Integer idRezervacija, Integer idNarudzbina){
        Optional<Konobar> konobar = konobarRepository.findById(idKonobar);
        if(konobar.isEmpty()){
            throw new EntityDoesNotExists("Konobar sa id " + idKonobar + " ne postoji.");
        }
        Optional<Rezervacijaracun> rr = rezervacijaracunRepository.findById(idRezervacija);
        if(rr.isEmpty()){
            throw new EntityDoesNotExists("Rezervacija sa id "+ idRezervacija + "ne postoji.");
        }
        Optional<Narudzbinaracun> nr = narudzbinaracunRepository.findById(idNarudzbina);
        if(nr.isEmpty()){
            throw new EntityDoesNotExists("Narudzbina sa id " + idNarudzbina + " ne postoji");
        }
        Racun racun = new Racun();
        racun.setKonobarIdkonobar(konobar.get());
        racun.setNarudzbinaimajelaracunIdnarudzbinaimajelaracun(nr.get());
        racun.setRezervacijaracunIdrezervacijaracun(rr.get());
        return racunRepository.save(racun);
    }

    public void obrisiRacun(Integer idRacun){
        Optional<Racun> racun = racunRepository.findById(idRacun);
        if(racun.isEmpty()){
            throw new EntityDoesNotExists("Racun sa id " + idRacun + " ne postoji.");
        }else{
            racunRepository.delete(racun.get());
        }
    }

    public Double prihodZaDatum(LocalDate datum){
        return racunRepository.prihodZaDatum(datum);
    }

    public List<LocalDate> daniSaNajviseRacuna(){
        return racunRepository.daniSaNajviseRacuna();
    }
}
