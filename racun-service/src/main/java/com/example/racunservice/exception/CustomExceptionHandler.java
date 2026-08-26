package com.example.racunservice.exception;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                            HttpHeaders headers, HttpStatusCode status,
                                                                            WebRequest request) {
        System.out.println("Pozivam handleMethodArgumentNotValid za izuzetak " + ex.getMessage());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ukupan broj gresaka: ").append(ex.getErrorCount()).append(", ");
        for (FieldError e : ex.getFieldErrors()) {
            stringBuilder.append("Polje: ").append(e.getField()).append(" - ").append(e.getDefaultMessage()).append(", ");
        }

        ErrorEntity errorEntity = new ErrorEntity(stringBuilder.toString(), LocalDateTime.now());
        return new ResponseEntity<>(errorEntity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityAlreadyExists.class)
    public final ResponseEntity<ErrorEntity> handleEntityAlreadyExists(EntityAlreadyExists ex) {
        ErrorEntity errorEntity = new ErrorEntity(ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorEntity, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityDoesNotExists.class)
    public final ResponseEntity<ErrorEntity> handleEntityDoesNotExist(EntityDoesNotExists ex) {
        ErrorEntity errorEntity = new ErrorEntity(ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorEntity, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorEntity> handleAllExceptions(Exception ex) {
        ErrorEntity errorEntity = new ErrorEntity(ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorEntity, HttpStatus.BAD_REQUEST);
    }

}
