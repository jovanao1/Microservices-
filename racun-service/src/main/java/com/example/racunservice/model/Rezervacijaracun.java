package com.example.racunservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "rezervacijaracun")
public class Rezervacijaracun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrezervacijaRacun", nullable = false)
    private Integer id;

    @Column(name = "datumvremeRezervacija", nullable = false)
    @NotNull
    private LocalDateTime datumvremeRezervacija;

}
