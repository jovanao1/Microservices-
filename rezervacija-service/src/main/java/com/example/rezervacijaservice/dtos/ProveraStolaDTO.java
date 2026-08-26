package com.example.rezervacijaservice.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProveraStolaDTO {
    @NotNull
    private LocalDateTime pocetak;
    @NotNull
    @Positive
    private Integer trajanje;
}
