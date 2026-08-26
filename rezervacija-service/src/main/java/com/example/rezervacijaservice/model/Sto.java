package com.example.rezervacijaservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sto")
public class Sto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSto", nullable = false)
    private Integer id;

    @Column(name = "brojstolicasto", nullable = false)
    @NotNull
    @PositiveOrZero
    private Integer brojstolicasto;

}
