package com.example.rezervacijaservice.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Setter
public class RezervacijaDTO {
    @NotNull
    @FutureOrPresent
    private LocalDateTime datumvremeRezervacija;
    @NotNull
    @Positive
    private Integer trajanjeRezervacija;
    @NotNull
    @NotBlank
    @Length(min =  3, max = 45)
    private String username;
    @NotNull
    @Positive
    private Integer stoId;
}
