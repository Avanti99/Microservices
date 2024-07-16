package com.example.UserService.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException() {
        super("User not found on server !!");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
