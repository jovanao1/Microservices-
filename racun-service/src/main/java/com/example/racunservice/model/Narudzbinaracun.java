package com.example.racunservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "narudzbinaracun")
public class Narudzbinaracun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnaruzbina", nullable = false)
    private Integer id;

    @Column(name = "ukupnaCenaNarudzbina", nullable = false)
    @NotNull
    @Positive
    private Double ukupnaCenaNarudzbina;

}
