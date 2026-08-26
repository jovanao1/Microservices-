package com.example.racunservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "racun")
public class Racun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idracun", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "konobar_idkonobar", nullable = false)
    @NotNull
    private Konobar konobarIdkonobar;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rezervacijaRacun_idrezervacijaRacun", nullable = false)
    @NotNull
    private Rezervacijaracun rezervacijaracunIdrezervacijaracun;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "narudzbinaImaJelaRacun_idnarudzbinaImaJelaRacun", nullable = false)
    @NotNull
    private Narudzbinaracun narudzbinaimajelaracunIdnarudzbinaimajelaracun;

}
