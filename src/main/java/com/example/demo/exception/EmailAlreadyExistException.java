package com.example.demo.exception;

public class EmailAlreadyExistException extends CustomException{
    public EmailAlreadyExistException(String message) {
        super(message, "EMAIL_ALREADY_EXISTS");
    }
}
