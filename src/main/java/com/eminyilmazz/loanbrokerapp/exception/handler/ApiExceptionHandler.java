package com.eminyilmazz.loanbrokerapp.exception.handler;

import com.eminyilmazz.loanbrokerapp.exception.DuplicateTcknException;
import com.eminyilmazz.loanbrokerapp.exception.IllegalTcknException;
import com.eminyilmazz.loanbrokerapp.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler({MethodArgumentNotValidException.class})
    private ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> error = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError) err).getField();
            String errorMessage = err.getDefaultMessage();
            error.put(fieldName, errorMessage);
        });
        logException(error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({NotFoundException.class})
    private ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());
        logException(error);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler({IllegalTcknException.class})
    private ResponseEntity<Map<String, String>> handleIllegalTcknException(IllegalTcknException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());
        logException(error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    private ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());
        logException(error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({DuplicateTcknException.class})
    private ResponseEntity<Map<String, String>> handleDuplicateTcknException(DuplicateTcknException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());
        logException(error);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler({Exception.class})
    private ResponseEntity<Map<String, String>> handleException(DuplicateTcknException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());
        logException(error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    private void logException(Map<String, String> error) {
        try {
            logger.error("Error occurred during the request: {}", objectMapper.writeValueAsString(error));
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
