package com.example.racunservice.controller;

import com.example.racunservice.model.Narudzbinaracun;
import com.example.racunservice.service.NarudzbinaracunService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/narudzbinaracun")
@RequiredArgsConstructor
@Validated
public class NarudzbinaracunController {
    private final NarudzbinaracunService nrs;

    //poziva ga preko feign
    @PostMapping("/dodajNarudzbinu/{ukupnaCena}")
    public Narudzbinaracun dodajNarudzbinu(@PathVariable @NotNull @Positive Double ukupnaCena){
        Narudzbinaracun nr = nrs.dodajNarudzbina(ukupnaCena);
        return nr;
    }
}
