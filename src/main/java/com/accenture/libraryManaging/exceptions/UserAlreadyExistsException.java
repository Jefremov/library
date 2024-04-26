package com.accenture.libraryManaging.exceptions;

public class UserAlreadyExistsException extends Exception{

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
