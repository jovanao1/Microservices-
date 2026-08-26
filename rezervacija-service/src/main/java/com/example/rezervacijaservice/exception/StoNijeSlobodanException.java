package com.example.rezervacijaservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StoNijeSlobodanException extends RuntimeException{
    private String message;
}
