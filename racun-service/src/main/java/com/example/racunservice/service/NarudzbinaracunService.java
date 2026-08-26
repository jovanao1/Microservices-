package com.example.racunservice.service;

import com.example.racunservice.model.Narudzbinaracun;
import com.example.racunservice.repositories.NarudzbinaracunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NarudzbinaracunService {
    private final NarudzbinaracunRepository nbr;

    public Narudzbinaracun dodajNarudzbina(Double cena){
        Narudzbinaracun nr = new Narudzbinaracun();
        nr.setUkupnaCenaNarudzbina(cena);
        return nbr.save(nr);
    }
}
