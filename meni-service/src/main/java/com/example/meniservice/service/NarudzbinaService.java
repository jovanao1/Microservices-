package com.example.meniservice.service;
import com.example.meniservice.dtos.NarucivanjeDTO;
import com.example.meniservice.dtos.NarudzbinaracunDTO;
import com.example.meniservice.exception.EntityDoesNotExists;
import com.example.meniservice.feign.RacunProxy;
import com.example.meniservice.model.Jelo;
import com.example.meniservice.model.Narudzbina;
import com.example.meniservice.model.Narudzbinaimajela;
import com.example.meniservice.repositories.JeloRepository;
import com.example.meniservice.repositories.NarudzbinaRepository;
import com.example.meniservice.repositories.NarudzbinaimajelaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class NarudzbinaService {
    private final NarudzbinaRepository narudzbinaRepository;
    private final NarudzbinaimajelaRepository narudzbinaimajelaRepository;
    private final JeloRepository jeloRepository;
    private final RacunProxy racunProxy;

  @Transactional
   public Narudzbina dodajNarudzbinu(NarucivanjeDTO narucivanjeDTO) {
        HashMap<String, Integer> jeloKolicina = narucivanjeDTO.getJeloKolicina();
        Narudzbina nar = new Narudzbina();
        double ukupnaCenaNarudzbine = 0.0;
        for (String jelo : jeloKolicina.keySet()) {
            Jelo j = jeloRepository.findJeloByNazivJelo(jelo);
            if (j == null) {
                throw new EntityDoesNotExists("Jelo " + jelo + " ne postoji.");
            }
            ukupnaCenaNarudzbine += jeloKolicina.get(jelo) * j.getCenaJelo();
        }
        nar.setUkupnaCenaNarudzbina(ukupnaCenaNarudzbine);
        narudzbinaRepository.save(nar);

         for (String jelo : jeloKolicina.keySet()) {

            Narudzbinaimajela nij = new Narudzbinaimajela();
            Jelo j = jeloRepository.findJeloByNazivJelo(jelo);

            nij.setJeloIdjelo(j);
            nij.setNarudzbinaIdnarudzbina(nar);
            nij.setKolicinaJela(jeloKolicina.get(jelo));

            narudzbinaimajelaRepository.save(nij);
        }
        NarudzbinaracunDTO nrdto = racunProxy.dodajNarudzbinu(ukupnaCenaNarudzbine);
        if(nrdto == null){
            throw new RuntimeException("Nije moguce kreirati narudzbinu za racun.");
        }
        return narudzbinaRepository.save(nar);
    }

}
