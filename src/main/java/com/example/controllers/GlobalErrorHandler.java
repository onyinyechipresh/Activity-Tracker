package com.example.controllers;


import com.example.dtos.ErrorResponse;
import com.example.exceptions.CustomAppException;
import com.example.exceptions.ResourceAlreadyExistsException;
import com.example.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler({ResourceAlreadyExistsException.class})
    public ResponseEntity<?> handleResourceAlreadyExistsException(final ResourceAlreadyExistsException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setDebugMessage("Please sign up with another email");
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<?> handleResourceNotFoundException(final ResourceNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setDebugMessage("Please check the login details and try again");
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler({CustomAppException.class})
    public ResponseEntity<?> handleCustomAppException(final CustomAppException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setDebugMessage("Task does not exist");
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}

