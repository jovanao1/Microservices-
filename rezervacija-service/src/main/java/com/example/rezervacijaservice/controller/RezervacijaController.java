package com.example.rezervacijaservice.controller;

import com.example.rezervacijaservice.dtos.RezervacijaDTO;
import com.example.rezervacijaservice.dtos.RezervacijaPoMesecuDTO;
import com.example.rezervacijaservice.model.Rezervacija;
import com.example.rezervacijaservice.service.RezervacijaService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rezervacija")
@RequiredArgsConstructor
@Validated
public class RezervacijaController {
    private final RezervacijaService rezervacijaService;

    /* primer requestBody za poziv metoda
    {
  "datumvremeRezervacija" : "2026-02-01T19:30:00Z",
   "trajanjeRezervacija" : 120,
    "username" : "jankoj",
    "stoId":1
}*/
    @PostMapping("/rezervisi")
    public ResponseEntity<Rezervacija> dodajRezervaciju(@Valid @RequestBody RezervacijaDTO rezervacija) {
        Rezervacija r = rezervacijaService.dodajRezervaciju(rezervacija);
        if(r != null) {
            return ResponseEntity.ok(r);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/pregledRezervacija")
    public List<RezervacijaDTO> pregledRezervacija(){
       List<Rezervacija> rezervacije =  rezervacijaService.pregledRezervacija();
       List<RezervacijaDTO> dtos = new ArrayList<>();
       for(Rezervacija r : rezervacije){
           RezervacijaDTO dto = new RezervacijaDTO();
           dto.setTrajanjeRezervacija(r.getTrajanjeRezervacija());
           dto.setDatumvremeRezervacija(r.getDatumvremeRezervacija());
           dto.setUsername(r.getKorisnikIdkorisnik().getUsernamekorisnik());
           dto.setStoId(r.getStoIdsto().getId());
           dtos.add(dto);
       }
       return dtos;
    }

    @GetMapping("/rezervacijePoMesecimaU2026")
    public List<RezervacijaPoMesecuDTO> rezervacijePoMesecimaU2026(){
        return rezervacijaService.rezervacijePoMesecimaU2026();
    }

    /* "username" : "jankoj",
    "datum" : "2026-02-01T19:30:00Z"*/
    @DeleteMapping("/otkaziRezervaciju")
    //circuit breaker jer radi sa vise mikroservisa
    @CircuitBreaker(name = "otkaziRezervaciju", fallbackMethod = "otkaziRezervacijuFallback")
    public ResponseEntity<String> otkaziRezervaciju(@RequestParam("username") @NotNull @NotBlank @Length(min = 3, max = 45)
                                                        String username, @RequestParam
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime datum ) {
        boolean otkazano = rezervacijaService.otkaziRezervaciju(username, datum);
        if(otkazano){
            return ResponseEntity.ok("Uspesno otkazana rezervacija.");
        }else{
            return ResponseEntity.badRequest().body("Niste uspesno otkazali rezervaciju. Proverite podatke");
        }
    }

    public ResponseEntity<String> otkaziRezervacijuFallback(Exception e){
        System.out.println("Pozvan fallback metod otkaziRezervaciju sa CircuitBreaker.");
        return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
    }
}
