package com.example.meniservice.dtos;

import com.example.meniservice.model.Jelo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JeloMeniDTO {
    private String kategorija;
    private String opisKategorija;
    private String naziv;
    private Double cena;
    private String opis;
}
