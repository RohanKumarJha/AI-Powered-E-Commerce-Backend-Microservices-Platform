package com.ecommerce.exception;

import com.ecommerce.dto.response.ExceptionResponse;
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
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(
            ResourceNotFoundException exception
    ) {

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



    @ExceptionHandler(NotificationFailedException.class)
    public ResponseEntity<ExceptionResponse> handleNotificationFailedException(
            NotificationFailedException exception
    ) {

        ExceptionResponse response =
                ExceptionResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message(exception.getMessage())
                        .details("Notification delivery failed")
                        .errorCode("NOTIFICATION_FAILED")
                        .build();


        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(
            MethodArgumentNotValidException exception
    ) {


        Map<String, String> errors = new HashMap<>();


        exception.getBindingResult()
                .getFieldErrors()
                .forEach(
                        error ->
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




    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGlobalException(
            Exception exception
    ) {


        ExceptionResponse response =
                ExceptionResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message(exception.getMessage())
                        .details("Internal server error")
                        .errorCode("INTERNAL_ERROR")
                        .build();


        return new ResponseEntity<>(
                response,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}