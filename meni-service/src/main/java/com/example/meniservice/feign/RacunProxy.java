package com.example.meniservice.feign;

import com.example.meniservice.dtos.NarudzbinaracunDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@FeignClient(name = "racun-service")
public interface RacunProxy {

    @PostMapping("/narudzbinaracun/dodajNarudzbinu/{ukupnaCena}")
    public NarudzbinaracunDTO dodajNarudzbinu(@PathVariable Double ukupnaCena);
}
