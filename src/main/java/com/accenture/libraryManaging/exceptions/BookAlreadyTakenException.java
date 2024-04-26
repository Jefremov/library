package com.accenture.libraryManaging.exceptions;

public class BookAlreadyTakenException extends Exception{

    public BookAlreadyTakenException(String errorMessage){
        super(errorMessage);
    }
}
