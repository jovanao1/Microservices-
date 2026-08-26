package com.example.racunservice.controller;

import com.example.racunservice.model.Rezervacijaracun;
import com.example.racunservice.service.RezervacijaracunService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/rezervacijaracun")
@RequiredArgsConstructor
@Validated
public class RezervacijaracunController {
    private final RezervacijaracunService rs;

    //poziva ga preko feign
    @PostMapping("/dodajRezervaciju")
    public Rezervacijaracun dodajRezervaciju(@RequestBody @NotNull LocalDateTime datumvremeRezervacija){
        return rs.dodajRezervacijaRacun(datumvremeRezervacija);
    }

    //poziva ga preko feign
    @DeleteMapping("/obrisiRezervaciju/{id}")
    public void obrisiRezervaciju(@PathVariable @NotNull @Positive Integer id){
        rs.obrisiRezervacijaRacun(id);
    }
}
