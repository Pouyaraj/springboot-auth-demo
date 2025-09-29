package com.example.demo.exception;

public class EmptyPasswordException extends CustomException{
    public EmptyPasswordException(String message){
        super(message, "EMPTY_PASSWORD");
    }
}