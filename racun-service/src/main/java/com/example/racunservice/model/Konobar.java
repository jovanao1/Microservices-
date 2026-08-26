package com.example.racunservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "konobar")
public class Konobar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkonobar", nullable = false)
    private Integer id;

    @Column(name = "imeKonobar", nullable = false, length = 45)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 45)
    private String imeKonobar;

}
