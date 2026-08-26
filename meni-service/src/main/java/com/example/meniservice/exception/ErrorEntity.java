package com.example.meniservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorEntity {
    private String poruka;
    private LocalDateTime datumIVreme;
}
