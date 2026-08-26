package com.example.meniservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EntityAlreadyExists extends  RuntimeException{
    private String message;
}
