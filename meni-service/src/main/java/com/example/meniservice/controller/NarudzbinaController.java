package com.example.meniservice.controller;

import com.example.meniservice.dtos.NarucivanjeDTO;
import com.example.meniservice.model.Narudzbina;
import com.example.meniservice.service.NarudzbinaService;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/narudzbina")
@RequiredArgsConstructor
public class NarudzbinaController {
    private final NarudzbinaService narudzbinaService;

    @PostMapping("/naruci")
    /* primer RequestBody za poziv metode
    {
  "jeloKolicina": {
    "Ruske kape": 2,
    "Tri lece": 1
  }
}*/
    public ResponseEntity<Narudzbina> dodajNarudzbinu(@Valid @RequestBody NarucivanjeDTO narucivanjeDTO) {
        Narudzbina nar = narudzbinaService.dodajNarudzbinu(narucivanjeDTO);
        if(nar == null) {
            return ResponseEntity.badRequest().build();
        }else{
            return  ResponseEntity.ok(nar);
        }
    }

}
