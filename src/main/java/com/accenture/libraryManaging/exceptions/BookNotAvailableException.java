package com.accenture.libraryManaging.exceptions;

public class BookNotAvailableException extends Exception{

        public BookNotAvailableException(String errorMessage){
            super(errorMessage);
        }
}
