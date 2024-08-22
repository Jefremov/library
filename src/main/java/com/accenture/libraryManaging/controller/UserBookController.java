package com.accenture.libraryManaging.controller;


import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.service.*;
import io.swagger.v3.oas.annotations.*;
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

    @Operation(summary = "Get a book by username and ISBN")
    @PostMapping("/getBook/{username}/{isbn}")
    public ResponseEntity<?> getBook(@PathVariable String username, @PathVariable String isbn)
            throws BookNotFoundException, BookNotAvailableException, UserNotFoundException, BookAlreadyTakenException {
        return new ResponseEntity<>(userBookService.getBook(username, isbn), HttpStatus.OK);
    }

    @Operation(summary = "Return a book by username and ISBN")
    @PostMapping("/returnBook/{username}/{isbn}")
    public ResponseEntity<?> returnBook(@PathVariable String username, @PathVariable String isbn)
            throws BookNotFoundException, UserNotFoundException, BookAlreadyTakenException, BookNotAvailableException {
        return new ResponseEntity<>(userBookService.returnBook(username, isbn), HttpStatus.OK);
    }
    @Operation(summary = "Order a book by username and ISBN. If the book is already taken, it will be ordered.")
    @PostMapping("/orderBook/{username}/{isbn}")
    public ResponseEntity<?> orderBook(@PathVariable String username, @PathVariable String isbn)
            throws BookNotFoundException, UserNotFoundException, BookAlreadyTakenException, BookNotAvailableException {
        return new ResponseEntity<>(userBookService.orderBook(username, isbn), HttpStatus.OK);
    }
}
