package com.example.meniservice.controller;

import com.example.meniservice.dtos.BestSellerJeloDTO;
import com.example.meniservice.dtos.JeloMeniDTO;
import com.example.meniservice.model.Jelo;
import com.example.meniservice.repositories.NarudzbinaimajelaRepository;
import com.example.meniservice.service.JeloService;
import com.example.meniservice.service.NarudzbinaImaJelaService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jelo")
@RequiredArgsConstructor
@Validated
public class JeloController {
    private final JeloService jeloService;
    private final NarudzbinaImaJelaService  narudzbinaImaJelaService;
    /*primer request body za poziv metode
    {
  "nazivJelo":"Ruske kape",
  "opisJelo":"Sastoje se od dva biskvitna diska spojenih filom od vanile, premazanih kremom sa strane i uvaljanih u kokosovo brašno, dok je vrh preliven čokoladnom glazurom.",
  "cenaJelo":350.00,
  "kategorijajelaIdkategorijajela":{
    	"id":3
  	}
}*/
    @PostMapping("/dodajJelo")
    public ResponseEntity<Jelo> dodajJelo(@Valid @RequestBody Jelo jelo) {
        Jelo novo = jeloService.dodajJelo(jelo);
        if(novo != null){
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pregledMenija")
    public List<JeloMeniDTO> pregledMenija(){
        return jeloService.pregledMenija();
    }

    @GetMapping("/jelaZaKategoriju/{kategorija}")
    public List<JeloMeniDTO> jelaZaKategoriju(@PathVariable @NotNull @NotBlank @Length(min = 3, max = 45)
                                                  String kategorija){
        return jeloService.jelaZaKategoriju(kategorija);
    }

    @PatchMapping("/promeniCenuJela")
    @RateLimiter(name="promeniCenuJela", fallbackMethod = "promeniCenuJelaFallback")
    public ResponseEntity<?> promeniCenuJela(@RequestParam("nazivJela") @NotNull @NotBlank @Length(min = 3, max = 45)
                                                 String nazivJela, @RequestParam("cenaJela") @NotNull @NotBlank String cenaJela){
        System.out.println("promeniCenuJela metod sa rateLimiter je pozvan.");
        boolean promenjeno = jeloService.promeniCenuJela(nazivJela, Double.valueOf(Double.parseDouble(cenaJela)));
        if(promenjeno){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> promeniCenuJelaFallback(Exception ex){
        System.out.println("promeniCenuJelaFallback metod je pozvan.");
        return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
    }

    @GetMapping("/opisICenaJela/{nazivJela}")
    public String opisICenaJela(@PathVariable @NotNull @NotBlank @Length(min = 3, max = 45) String nazivJela){
        return jeloService.opisICenaJela(nazivJela);
    }

    @GetMapping("/bestseleri")
    public List<BestSellerJeloDTO> bestseleri(){
        return narudzbinaImaJelaService.bestseleri();
    }

}
