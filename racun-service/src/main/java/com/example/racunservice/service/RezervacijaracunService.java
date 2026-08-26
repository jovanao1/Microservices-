package com.example.racunservice.service;

import com.example.racunservice.exception.EntityDoesNotExists;
import com.example.racunservice.model.Rezervacijaracun;
import com.example.racunservice.repositories.RezervacijaracunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RezervacijaracunService {
    private final RezervacijaracunRepository rezervacijaracunRepository;

    public Rezervacijaracun dodajRezervacijaRacun(LocalDateTime datumVreme){
        Rezervacijaracun rr = new  Rezervacijaracun();
        rr.setDatumvremeRezervacija(datumVreme);
        return rezervacijaracunRepository.save(rr);
    }

   public void obrisiRezervacijaRacun(Integer id){
        if(rezervacijaracunRepository.findById(id).isEmpty()){
            throw new EntityDoesNotExists("Rezervacija sa id " + id + " nije pronadjena.");
        }
        rezervacijaracunRepository.deleteById(id);
    }
}
