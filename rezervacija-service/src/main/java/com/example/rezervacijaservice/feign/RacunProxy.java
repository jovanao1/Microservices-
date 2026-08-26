package com.example.rezervacijaservice.feign;

import com.example.rezervacijaservice.dtos.RezervacijaracunDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@FeignClient(name = "racun-service")
public interface RacunProxy {
    @PostMapping("/rezervacijaracun/dodajRezervaciju")
    public RezervacijaracunDTO dodajRezervaciju(@RequestBody LocalDateTime datumvremeRezervacija);

    @DeleteMapping("/rezervacijaracun/obrisiRezervaciju/{id}")
    public void obrisiRezervaciju(@PathVariable Integer id);
}
