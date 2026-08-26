package com.example.meniservice.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;

@Getter
@Setter
public class NarucivanjeDTO {
    @NotNull
    HashMap<String, Integer> jeloKolicina;
}
