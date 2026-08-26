package com.example.rezervacijaservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "rezervacija")
public class Rezervacija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrezervacija", nullable = false)
    private Integer id;

    @Column(name = "datumvremeRezervacija", nullable = false)
    @NotNull
    private LocalDateTime datumvremeRezervacija;

    @Column(name = "trajanjeRezervacija")
    @NotNull
    @Positive
    private Integer trajanjeRezervacija;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "korisnik_idkorisnik", nullable = false)
    @NotNull
    private Korisnik korisnikIdkorisnik;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Sto_idSto", nullable = false)
    @NotNull
    private Sto stoIdsto;

}
