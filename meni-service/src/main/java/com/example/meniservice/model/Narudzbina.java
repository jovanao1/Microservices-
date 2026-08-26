package com.example.meniservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "narudzbina")
public class Narudzbina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnarudzbina", nullable = false)
    private Integer id;

    @Column(name = "ukupnaCenaNarudzbina")
    @NotNull
    @Positive
    private Double ukupnaCenaNarudzbina;

}
