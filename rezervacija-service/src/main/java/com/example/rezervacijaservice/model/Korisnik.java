package com.example.rezervacijaservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "korisnik")
public class Korisnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkorisnik", nullable = false)
    private Integer id;

    @Column(name = "imekorisnik", nullable = false, length = 45)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 45)
    private String imekorisnik;

    @Column(name = "prezimekorisnik", nullable = false, length = 45)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 45)
    private String prezimekorisnik;

    @Column(name = "usernamekorisnik", nullable = false, length = 45)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 45)
    private String usernamekorisnik;

    @Column(name = "emailkorisnik", nullable = false, length = 45)
    @NotNull
    @Email
    @NotBlank
    @Size(min = 1, max = 45)
    private String emailkorisnik;

    @Column(name = "passwordkorisnik", nullable = false, length = 45)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 45)
    private String passwordkorisnik;

}
