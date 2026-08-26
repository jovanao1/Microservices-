package com.example.rezervacijaservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RezervacijaPoMesecuDTO {
    private Integer mesec;
    private Integer brojRezervacija;
}
