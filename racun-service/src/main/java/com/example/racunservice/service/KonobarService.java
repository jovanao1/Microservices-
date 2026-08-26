package com.example.racunservice.service;

import com.example.racunservice.exception.EntityAlreadyExists;
import com.example.racunservice.model.Konobar;
import com.example.racunservice.repositories.KonobarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KonobarService {
    private final KonobarRepository konobarRepository;

    public Konobar dodajKonobara(String ime){
        if(konobarRepository.findByImeKonobar(ime) != null){
            throw new EntityAlreadyExists("Konobar " + ime + " vec postoji, identifikujte ga drugacije.");
        }
        Konobar k = new  Konobar();
        k.setImeKonobar(ime);
        return  konobarRepository.save(k);
    }

    public boolean konobarPostoji(Integer id, String ime){
        return konobarRepository.existsByIdAndImeKonobar(id, ime);
    }
}
