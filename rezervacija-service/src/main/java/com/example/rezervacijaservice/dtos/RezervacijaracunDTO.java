package com.example.rezervacijaservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RezervacijaracunDTO {
    private Integer id;
    private LocalDateTime datumvremeRezervacija;
}
