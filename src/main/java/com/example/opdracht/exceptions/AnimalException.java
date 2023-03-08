package com.example.opdracht.exceptions;

public class AnimalException extends RuntimeException {
    private String message;

    public AnimalException(String message) {
        super(message);
        this.message = message;
    }

    public AnimalException() {

    }
}
