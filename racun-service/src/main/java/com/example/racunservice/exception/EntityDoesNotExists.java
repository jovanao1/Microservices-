package com.example.racunservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EntityDoesNotExists extends RuntimeException{
    private String message;
}
