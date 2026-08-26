package com.example.racunservice.controller;

import com.example.racunservice.model.Konobar;
import com.example.racunservice.service.KonobarService;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/konobar")
@Validated
public class KonobarController {
    private final KonobarService konobarService;

    @PostMapping("/dodajKonobara/{ime}")
    public ResponseEntity<Konobar> dodajKonobara(@PathVariable @NotNull @NotBlank @Size(min = 3, max = 45) String ime){
        Konobar k = konobarService.dodajKonobara(ime);
        if(k != null){
            return new ResponseEntity<>(k, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Retry(name="postojiKonobar", fallbackMethod = "postojiKonobarFallback")
    @GetMapping("/postojiKonobar")
    public ResponseEntity<Boolean> konobarPostoji(@RequestParam Integer id, @RequestParam String ime){
        System.out.println("Pozvana metoda postojiKonobar sa Retry.");
        return new ResponseEntity<>(konobarService.konobarPostoji(id, ime), HttpStatus.OK);
    }

    public ResponseEntity<Boolean> postojiKonobarFallback(Exception ex){
        System.out.println("Pozvana fallback metoda postojiKonobar sa Retry.");
        return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
    }
}
