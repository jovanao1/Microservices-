package com.example.rezervacijaservice.service;

import com.example.rezervacijaservice.exception.EntityDoesNotExists;
import com.example.rezervacijaservice.model.Sto;
import com.example.rezervacijaservice.repositories.StoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoService {
    private final StoRepository stoRepository;

    public Sto dodajSto(Integer brojStolica) {
        Sto sto = new Sto();
        sto.setBrojstolicasto(brojStolica);
        return stoRepository.save(sto);
    }

    public List<Sto> slobodniStoloviZaPeriod(LocalDateTime pocetak, Integer trajanje) {
        LocalDateTime kraj = pocetak.plusMinutes(trajanje);
        return stoRepository.findSlobodniStolovi(pocetak, kraj);
    }

    @Transactional
    public Sto promeniBrojStolica(Integer idSto, Integer brojStolica) {
        Optional<Sto> s = stoRepository.findById(idSto);
        if(s.isPresent()){
            Sto sto = s.get();
            sto.setBrojstolicasto(brojStolica);
            return stoRepository.save(sto);
        }else{
            throw new EntityDoesNotExists("Sto sa id " + idSto + " ne postoji.");
        }
    }
}
