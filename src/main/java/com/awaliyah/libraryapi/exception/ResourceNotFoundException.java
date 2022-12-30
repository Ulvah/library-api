package com.awaliyah.libraryapi.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String massage){
        super(massage);
    }
}
