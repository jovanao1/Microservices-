package com.example.meniservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "narudzbinaimajela")
public class Narudzbinaimajela {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnarudzbinaimajela", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jelo_idjelo", nullable = false)
    @NotNull
    private Jelo jeloIdjelo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "narudzbina_idnarudzbina", nullable = false)
    @NotNull
    private Narudzbina narudzbinaIdnarudzbina;

    @Column(name = "kolicinaJela", nullable = false)
    @NotNull
    @Positive
    private Integer kolicinaJela;

}
