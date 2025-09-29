package com.example.demo.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

        @org.springframework.web.bind.annotation.ExceptionHandler(EmailAlreadyExistException.class)
        public ResponseEntity<Map<String, Object>> handleEmailExists(EmailAlreadyExistException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("errorCode", ex.getErrorCode());
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EmptyPasswordException.class)
    public ResponseEntity<Map<String, Object>> handleEmptyPassword(EmptyPasswordException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("errorCode", ex.getErrorCode());
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
