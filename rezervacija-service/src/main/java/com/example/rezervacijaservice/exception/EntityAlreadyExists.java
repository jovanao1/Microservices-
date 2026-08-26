package com.example.rezervacijaservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EntityAlreadyExists extends RuntimeException{
    private String message;
}
