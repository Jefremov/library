package com.accenture.libraryManaging.exceptions;

import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(
            GlobalExceptionHandler.class);

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException exception){
        ResponseEntity<String> response = new ResponseEntity<>(exception.getStatusText(), exception.getStatusCode());
        log.warn(response.toString());
        return response;
    }

    //handle BookNotFoundException
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException exception){
        ResponseEntity<String> response = new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        log.warn(response.toString());
        return response;
    }

    //handle BookNotAvailableException

    @ExceptionHandler(BookNotAvailableException.class)
    @ResponseBody
    public ResponseEntity<String> handleBookNotAvailableException(BookNotAvailableException exception){
        ResponseEntity<String> response = new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        log.warn(response.toString());
        return response;
    }

    //handle IllegalGenreException
    @ExceptionHandler(IllegalGenreException.class)
    @ResponseBody
    public ResponseEntity<String> handleIllegalGenreException(IllegalGenreException exception){
        ResponseEntity<String> response = new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        log.warn(response.toString());
        return response;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException exception){
        ResponseEntity<String> response = new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        log.warn(response.toString());
        return response;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception){
        ResponseEntity<String> response = new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        log.warn(response.toString());
        return response;
    }

    @ExceptionHandler(BookAlreadyTakenException.class)
    @ResponseBody
    public ResponseEntity<String> handleBookAlreadyTakenException(BookAlreadyTakenException exception){
        ResponseEntity<String> response = new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        log.warn(response.toString());
        return response;
    }

    @ExceptionHandler(BookNotTakenException.class)
    @ResponseBody
    public ResponseEntity<String> handleBookNotTakenException(BookNotTakenException exception){
        ResponseEntity<String> response = new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        log.warn(response.toString());
        return response;
    }

}