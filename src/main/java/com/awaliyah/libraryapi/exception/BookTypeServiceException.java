package com.awaliyah.libraryapi.exception;

public class BookTypeServiceException extends RuntimeException{
    public BookTypeServiceException(String massage) {
        super(massage);
    }
}
