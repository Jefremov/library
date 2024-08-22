package com.accenture.library.exceptions;

public class BookAlreadyTakenException extends Exception{

    public BookAlreadyTakenException(String errorMessage){
        super("Book with isbn: " + errorMessage);
    }
}
