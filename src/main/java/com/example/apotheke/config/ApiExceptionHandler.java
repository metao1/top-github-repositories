package com.example.apotheke.config;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({CallNotPermittedException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public void handleCallLimitException() {
    }

    @ExceptionHandler({WebClientResponseException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public void handleWebClientResponseException() {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleConstraintViolationException(ConstraintViolationException e) {
    }
}