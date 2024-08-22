package com.accenture.library.exceptions;

public class BookNotAvailableException extends Exception{

        public BookNotAvailableException(String errorMessage){
            super(errorMessage);
        }
}
