package com.awaliyah.libraryapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class ExceptionalResponse {

    private LocalDateTime timestamp;
    private String massage;
    private HttpStatus status;

}
