package com.example.rezervacijaservice.controller;

import com.example.rezervacijaservice.model.Korisnik;
import com.example.rezervacijaservice.service.KorisnikService;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/korisnik")
public class KorisnikController {
    private final KorisnikService korisnikService;

    /* primer RequestBody za poziv metoda
    {
  "imekorisnik":"Janko",
  "prezimekorisnik":"Jankovic",
  "usernamekorisnik":"jankoj",
  "passwordkorisnik":"jankoj",
  "emailkorisnik":"jankoj@gmail.com"
}
    */
    @PostMapping("/kreirajNalog")
    public ResponseEntity<Korisnik> kreirajNalog(@Valid @RequestBody Korisnik korisnik){
       Korisnik k=  korisnikService.dodajKorisnika(korisnik);
        if(k != null){
            return ResponseEntity.ok(k);
        }else{
            return new  ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Retry(name="postojiKorisnik", fallbackMethod = "postojiKorisnikFallback")
    @GetMapping("/postojiKorisnik")
    public ResponseEntity<Boolean> postojiKorisnik(@RequestParam  String username, @RequestParam String password){
        System.out.println("Pozvan metod postojiKorisnik sa Retry.");
        return new ResponseEntity<>(korisnikService.postojiKorisnik(username, password), HttpStatus.OK);
    }

    public ResponseEntity<Boolean> postojiKorisnikFallback(Exception ex){
        System.out.println("Pozvan fallback metod postojiKorisnik sa Retry.");
        return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
    }
}
