package com.ecommerce.exception;


import com.ecommerce.dto.response.ExceptionResponse;

import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;



@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFound(ResourceNotFoundException exception){


        ExceptionResponse response =
                ExceptionResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message(exception.getMessage())
                        .details("Resource not found")
                        .errorCode("RESOURCE_NOT_FOUND")
                        .build();


        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND
        );

    }





    @ExceptionHandler(AIProviderException.class)
    public ResponseEntity<ExceptionResponse> handleAIProviderException(
            AIProviderException exception
    ){


        ExceptionResponse response =
                ExceptionResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message(exception.getMessage())
                        .details("AI provider failed")
                        .errorCode("AI_PROVIDER_ERROR")
                        .build();



        return new ResponseEntity<>(
                response,
                HttpStatus.SERVICE_UNAVAILABLE
        );

    }





    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(
            MethodArgumentNotValidException exception
    ){


        Map<String,String> errors =
                new HashMap<>();


        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );



        ExceptionResponse response =
                ExceptionResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message("Validation failed")
                        .details(errors.toString())
                        .errorCode("VALIDATION_ERROR")
                        .build();



        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );

    }





    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolation(
            ConstraintViolationException exception
    ){


        ExceptionResponse response =
                ExceptionResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message(exception.getMessage())
                        .details("Constraint violation")
                        .errorCode("CONSTRAINT_ERROR")
                        .build();



        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );

    }





    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGlobalException(
            Exception exception
    ){


        ExceptionResponse response =
                ExceptionResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message(exception.getMessage())
                        .details("Unexpected error occurred")
                        .errorCode("INTERNAL_SERVER_ERROR")
                        .build();



        return new ResponseEntity<>(
                response,
                HttpStatus.INTERNAL_SERVER_ERROR
        );

    }

}