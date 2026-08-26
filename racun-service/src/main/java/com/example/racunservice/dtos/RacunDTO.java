package com.example.racunservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RacunDTO {
    @NotNull
    @Positive
    private Integer idKonobar;
    @NotNull
    @Positive
    private Integer idRezervacija;
    @NotNull
    @Positive
    private Integer idNarudzbina;
}
