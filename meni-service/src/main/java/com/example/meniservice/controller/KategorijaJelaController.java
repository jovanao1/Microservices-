package com.example.meniservice.controller;

import com.example.meniservice.model.Kategorijajela;
import com.example.meniservice.service.KategorijaJelaService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/kategorija")
@Validated
public class KategorijaJelaController {
    private final KategorijaJelaService kategorijaJelaService;

    @PostMapping("/dodajKategoriju/{naziv}")
    public ResponseEntity<Kategorijajela> dodajKategoriju(@PathVariable("naziv") @NotNull @NotBlank
                                                              @Length(min = 3, max = 45) String naziv,
                                                          @RequestBody @NotBlank @NotNull @Length(min = 3, max=200) String opis) {
        Kategorijajela novaKategorija = kategorijaJelaService.dodajKategoriju(naziv, opis);
        if(novaKategorija != null) {
            return new ResponseEntity<>(novaKategorija, HttpStatus.CREATED);
        }else{
            return new  ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
