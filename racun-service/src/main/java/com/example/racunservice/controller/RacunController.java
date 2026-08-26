package com.example.racunservice.controller;

import com.example.racunservice.dtos.RacunDTO;
import com.example.racunservice.model.Racun;
import com.example.racunservice.service.RacunService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/racun")
@RequiredArgsConstructor
@Validated
public class RacunController {
    private final RacunService racunService;

    /*
    primer requestBody za poziv metoda
    {
	"idKonobar" : 1,
    "idRezervacija" : 3,
    "idNarudzbina" : 9
}
    */
    @PostMapping("/dodajRacun")
    @RateLimiter(name = "dodajRacun", fallbackMethod = "dodajRacunFallback")
    public ResponseEntity<Racun> dodajRacun(@Valid @RequestBody RacunDTO racun) {
        System.out.println("Pozvan metod dodajRacun sa RateLimiter.");
        Racun r = racunService.dodajRacun(racun.getIdKonobar(), racun.getIdRezervacija(), racun.getIdNarudzbina());
        if(r != null){
            return new ResponseEntity<>(r, HttpStatus.CREATED);
        }else{
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Racun> dodajRacunFallback(Exception e){
        System.out.println("Pozvan fallback metod dodajRacun sa RateLimiter.");
        return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
    }

    @DeleteMapping("/obrisiRacun/{idRacun}")
    public ResponseEntity<Racun> obrisiRacun(@PathVariable @NotNull @Positive Integer idRacun){
        racunService.obrisiRacun(idRacun);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/prihodZaDanZaljubljenih")
    public ResponseEntity<String> prihodZaDatum(){
        Double prihod = racunService.prihodZaDatum(LocalDate.of(2026, 2, 14));
        if(prihod != null){
            return ResponseEntity.ok().body("Prihod na Dan zaljubljenih 2026. godine je : " + prihod.toString());
        }else{
            return ResponseEntity.ok().body("Nije ostvaren prihod na Dan zaljubljenih 2026. godine");
        }
    }

    @GetMapping("/daniSaNajviseRacuna")
    public List<LocalDate> daniSaNajviseRacuna(){
        return racunService.daniSaNajviseRacuna();
    }
}
