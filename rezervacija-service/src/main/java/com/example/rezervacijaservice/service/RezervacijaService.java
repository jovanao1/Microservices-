package com.example.rezervacijaservice.service;

import com.example.rezervacijaservice.dtos.RezervacijaDTO;
import com.example.rezervacijaservice.dtos.RezervacijaPoMesecuDTO;
import com.example.rezervacijaservice.dtos.RezervacijaracunDTO;
import com.example.rezervacijaservice.exception.EntityDoesNotExists;
import com.example.rezervacijaservice.exception.StoNijeSlobodanException;
import com.example.rezervacijaservice.feign.RacunProxy;
import com.example.rezervacijaservice.model.Korisnik;
import com.example.rezervacijaservice.model.Rezervacija;
import com.example.rezervacijaservice.model.Sto;
import com.example.rezervacijaservice.repositories.KorisnikRepository;
import com.example.rezervacijaservice.repositories.RezervacijaRepository;
import com.example.rezervacijaservice.repositories.StoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RezervacijaService {
    private final RezervacijaRepository rezervacijaRepository;
    private final KorisnikRepository korisnikRepository;
    private final StoRepository stoRepository;
    private final RacunProxy racunProxy;

    public Rezervacija dodajRezervaciju(RezervacijaDTO rezervacija) {
        Korisnik k = korisnikRepository.findByUsernamekorisnik(rezervacija.getUsername());
        if(k == null){
            throw new EntityDoesNotExists("Korisnik " + rezervacija.getUsername() + " ne postoji.");
        }
        Optional<Sto> sto = stoRepository.findById(rezervacija.getStoId());
        if(sto.isPresent()) {
            Sto s = sto.get();
            LocalDateTime kraj = rezervacija.getDatumvremeRezervacija().plusMinutes(rezervacija.getTrajanjeRezervacija());
            if(!stoRepository.findSlobodniStolovi(rezervacija.getDatumvremeRezervacija(), kraj).contains(s)){
                throw new StoNijeSlobodanException("Sto nije slobodan za dato vreme.");
            }
            Rezervacija r = new Rezervacija();
            r.setDatumvremeRezervacija(rezervacija.getDatumvremeRezervacija());
            r.setKorisnikIdkorisnik(k);
            r.setStoIdsto(s);
            r.setTrajanjeRezervacija(rezervacija.getTrajanjeRezervacija());
            RezervacijaracunDTO rrdto = racunProxy.dodajRezervaciju(rezervacija.getDatumvremeRezervacija());
            if(rrdto == null){
                throw new RuntimeException("Nije moguce dodati rezervaciju.");
            }
            return rezervacijaRepository.save(r);
        }else{
            throw new EntityDoesNotExists("Sto " + rezervacija.getStoId() + "ne postoji.");
        }
    }

    public List<Rezervacija> pregledRezervacija(){
        return  rezervacijaRepository.findAll();
    }

    public List<RezervacijaPoMesecuDTO> rezervacijePoMesecimaU2026(){
        return rezervacijaRepository.rezervacijePoMesecimaU2026()
                .stream()
                .map(r -> new RezervacijaPoMesecuDTO(
                        ((Number) r[0]).intValue(),
                        ((Number) r[1]).intValue()
                ))
                .toList();
    }

    //obrisati i iz rezervacijaracun
    public boolean otkaziRezervaciju(String username, LocalDateTime datum){
        Korisnik kor = korisnikRepository.findByUsernamekorisnik(username);
        if(kor == null){
            throw new EntityDoesNotExists("Korisnik sa username " + username + " ne postoji.");
        }
        Rezervacija rez = rezervacijaRepository.findByKorisnikIdkorisnikAndDatumvremeRezervacija(kor, datum);
        if(rez == null){
            throw new EntityDoesNotExists("Rezervacija za datog korisnika i vreme ne postoji.");
        }else{
            racunProxy.obrisiRezervaciju(rez.getId());
            rezervacijaRepository.delete(rez);
            return true;
        }
    }
}
