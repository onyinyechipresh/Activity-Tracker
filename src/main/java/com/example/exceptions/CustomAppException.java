package com.example.exceptions;

public class CustomAppException extends RuntimeException{
    public CustomAppException(String message) {
        super(message);
    }
}
