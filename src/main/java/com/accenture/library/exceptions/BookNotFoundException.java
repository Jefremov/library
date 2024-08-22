package com.accenture.library.exceptions;

public class BookNotFoundException extends Exception {

    public BookNotFoundException(String errorMessage) {
        super(errorMessage + " not found");
    }
}
