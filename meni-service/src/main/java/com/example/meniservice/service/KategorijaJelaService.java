package com.example.meniservice.service;
import com.example.meniservice.exception.EntityAlreadyExists;
import com.example.meniservice.model.Kategorijajela;
import com.example.meniservice.repositories.KategorijajelaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KategorijaJelaService {
    private final  KategorijajelaRepository  kategorijajelaRepository;

    public Kategorijajela dodajKategoriju(String naziv, String opis){
        if(kategorijajelaRepository.findByNazivKategorijaJela(naziv) != null){
            throw new EntityAlreadyExists("Kategorija " + naziv + " vec postoji.");
        }
        Kategorijajela nova = new Kategorijajela();
        nova.setNazivKategorijaJela(naziv);
        nova.setOpisKategorijaJela(opis);
        Kategorijajela novaSacuvana = kategorijajelaRepository.save(nova);
        return novaSacuvana;
    }
}
