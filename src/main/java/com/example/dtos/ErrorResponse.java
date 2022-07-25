package com.example.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Getter @Setter
public class ErrorResponse {

    private String message;
    private HttpStatus status;
    private String debugMessage;
}
