package com.awaliyah.libraryapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleResourceNotFoundException(Exception ex, WebRequest request) throws Exception {
        log.error("An error happen when call API");
        return new ResponseEntity<>(new ExceptionalResponse(LocalDateTime.now(), ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(javax.validation.ValidationException.class)
    public final ResponseEntity<ExceptionalResponse> handleValidationException(ValidationException validationException) {
        return new ResponseEntity<>(new ExceptionalResponse(LocalDateTime.now(), validationException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY), HttpStatus.UNPROCESSABLE_ENTITY);

    }
}
