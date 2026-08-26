package com.example.meniservice.service;
import com.example.meniservice.dtos.BestSellerJeloDTO;
import com.example.meniservice.dtos.JeloMeniDTO;
import com.example.meniservice.exception.EntityDoesNotExists;
import com.example.meniservice.model.Jelo;
import com.example.meniservice.model.Kategorijajela;
import com.example.meniservice.repositories.JeloRepository;
import com.example.meniservice.repositories.KategorijajelaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JeloService {
    private final JeloRepository jeloRepository;
    private final KategorijajelaRepository kategorijajelaRepository;

    public Jelo dodajJelo(Jelo jelo) {
        return jeloRepository.save(jelo);
    }

    public List<JeloMeniDTO> pregledMenija(){
        List<JeloMeniDTO> jeloMeniDTOs = new ArrayList<>();
        List<Jelo> listaJela = jeloRepository.findAll();
        for(Jelo jelo : listaJela){
            JeloMeniDTO jeloMeniDTO = new JeloMeniDTO();
            jeloMeniDTO.setCena(jelo.getCenaJelo());
            jeloMeniDTO.setOpis(jelo.getOpisJelo());
            jeloMeniDTO.setNaziv(jelo.getNazivJelo());
            jeloMeniDTO.setKategorija(jelo.getKategorijajelaIdkategorijajela().getNazivKategorijaJela());
            jeloMeniDTO.setOpisKategorija(jelo.getKategorijajelaIdkategorijajela().getOpisKategorijaJela());
            jeloMeniDTOs.add(jeloMeniDTO);
        }
        return jeloMeniDTOs;
    }

    public List<JeloMeniDTO> jelaZaKategoriju(String kategorija){
        Kategorijajela kat = kategorijajelaRepository.findByNazivKategorijaJela(kategorija);
        if(kat == null){
            throw new EntityDoesNotExists("Kategorija sa nazivom " + kategorija + " ne postoji.");
        }
        List<Jelo> jela = jeloRepository.findByKategorijajelaIdkategorijajelaNazivKategorijaJela(kategorija);
        List<JeloMeniDTO> jeloMeniDTOs = new ArrayList<>();
        for (Jelo jelo : jela){
            JeloMeniDTO jeloMeniDTO = new JeloMeniDTO();
            jeloMeniDTO.setCena(jelo.getCenaJelo());
            jeloMeniDTO.setOpis(jelo.getOpisJelo());
            jeloMeniDTO.setNaziv(jelo.getNazivJelo());
            jeloMeniDTO.setKategorija(jelo.getKategorijajelaIdkategorijajela().getNazivKategorijaJela());
            jeloMeniDTO.setOpisKategorija(jelo.getKategorijajelaIdkategorijajela().getOpisKategorijaJela());
            jeloMeniDTOs.add(jeloMeniDTO);
        }
        return jeloMeniDTOs;
    }

    @Transactional
    public boolean promeniCenuJela(String nazivJelo, Double cenaJelo){
        Jelo jelo = jeloRepository.findJeloByNazivJelo(nazivJelo);
        if(jelo == null){
            throw new EntityDoesNotExists("Jelo " + nazivJelo + " ne postoji.");
        }
        jelo.setCenaJelo(cenaJelo);
        jeloRepository.save(jelo);
        return true;
    }

    public String opisICenaJela(String nazivJelo){
        Jelo jelo = jeloRepository.findJeloByNazivJelo(nazivJelo);
        if(jelo == null){
            throw new EntityDoesNotExists("Jelo " + nazivJelo + " ne postoji.");
        }else{
            return "Opis: " + jelo.getOpisJelo() + "  \n Cena: " + jelo.getCenaJelo();
        }
    }

}
