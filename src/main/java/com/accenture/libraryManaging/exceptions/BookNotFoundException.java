package com.accenture.libraryManaging.exceptions;

public class BookNotFoundException extends Exception {

    public BookNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
