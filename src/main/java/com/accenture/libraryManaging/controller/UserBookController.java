package com.accenture.libraryManaging.controller;


import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserBookController {

    private UserBookService userBookService;

    @Autowired
    public UserBookController(UserBookService bookService) {
        this.userBookService = bookService;
    }

    @PostMapping("/getBook/{username}/{isbn}")
    public ResponseEntity<?> getBook(@PathVariable String username, @PathVariable String isbn)
            throws BookNotFoundException, BookNotAvailableException, UserNotFoundException, BookAlreadyTakenException {
        return new ResponseEntity<>(userBookService.getBook(username, isbn), HttpStatus.OK);
    }

    @PostMapping("/returnBook/{username}/{isbn}")
    public ResponseEntity<?> returnBook(@PathVariable String username, @PathVariable String isbn)
            throws BookNotFoundException, UserNotFoundException, BookAlreadyTakenException, BookNotAvailableException {
        return new ResponseEntity<>(userBookService.returnBook(username, isbn), HttpStatus.OK);
    }

    @PostMapping("/orderBook/{username}/{isbn}")
    public ResponseEntity<?> orderBook(@PathVariable String username, @PathVariable String isbn)
            throws BookNotFoundException, UserNotFoundException, BookAlreadyTakenException, BookNotAvailableException {
        return new ResponseEntity<>(userBookService.orderBook(username, isbn), HttpStatus.OK);
    }
}
