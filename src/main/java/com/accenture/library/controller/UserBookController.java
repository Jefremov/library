package com.accenture.library.controller;


import com.accenture.library.exceptions.*;
import com.accenture.library.service.*;
import io.swagger.v3.oas.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserBookController {

    private final UserBookService userBookService;

    @Autowired
    public UserBookController(UserBookService bookService) {
        this.userBookService = bookService;
    }

    @Operation(summary = "Get a book by username and ISBN")
    @PostMapping("/getBook/{username}/{isbn}")
    public ResponseEntity<Object> getBook(@PathVariable String username, @PathVariable String isbn)
            throws BookNotFoundException, BookNotAvailableException, UserNotFoundException, BookAlreadyTakenException {
        return new ResponseEntity<>(userBookService.getBook(username, isbn), HttpStatus.OK);
    }

    @Operation(summary = "Return a book by username and ISBN")
    @PostMapping("/returnBook/{username}/{isbn}")
    public ResponseEntity<Object> returnBook(@PathVariable String username, @PathVariable String isbn)
            throws BookNotFoundException, UserNotFoundException, BookAlreadyTakenException, BookNotAvailableException {
        return new ResponseEntity<>(userBookService.returnBook(username, isbn), HttpStatus.OK);
    }
    @Operation(summary = "Order a book by username and ISBN. If the book is already taken, it will be ordered.")
    @PostMapping("/orderBook/{username}/{isbn}")
    public ResponseEntity<Object> orderBook(@PathVariable String username, @PathVariable String isbn)
            throws BookNotFoundException, UserNotFoundException, BookAlreadyTakenException, BookNotAvailableException {
        return new ResponseEntity<>(userBookService.orderBook(username, isbn), HttpStatus.OK);
    }
}
