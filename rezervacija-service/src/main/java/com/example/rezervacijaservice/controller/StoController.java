package com.example.rezervacijaservice.controller;

import com.example.rezervacijaservice.dtos.ProveraStolaDTO;
import com.example.rezervacijaservice.model.Sto;
import com.example.rezervacijaservice.service.StoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sto")
@Validated
public class StoController {
    private final StoService stoService;

    @PostMapping("/dodajSto")
    public ResponseEntity<Sto> dodajSto(@RequestParam("brojStolica") @NotNull @PositiveOrZero Integer brojStolica) {
        Sto k=  stoService.dodajSto(brojStolica);
        if(k != null){
            return ResponseEntity.ok(k);
        }else{
            return new  ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*{
  "pocetak": "2026-02-01T18:00:00",
  "trajanje": 90
}*/
    @PostMapping("/slobodniStolovi")
    public List<Sto> slobodniStolovi(@Valid @RequestBody ProveraStolaDTO provera) {
        LocalDateTime start = provera.getPocetak();
        return stoService.slobodniStoloviZaPeriod(start, provera.getTrajanje());
    }

    @PatchMapping("/promeniBrojStolica")
    public ResponseEntity<Sto> promeniBrojStolica(@RequestParam("idSto") @NotNull
                                                      String idSto, @RequestParam("brojStolica")  @NotNull
                                                    String brojStolica) {
        Integer idStola = Integer.parseInt(idSto);
        Integer brojStol = Integer.parseInt(brojStolica);
        Sto s = stoService.promeniBrojStolica(idStola, brojStol);
        if(s != null){
            return ResponseEntity.ok(s);
        }else{
            return new  ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
