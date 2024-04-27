package com.accenture.libraryManaging.controller;


import com.accenture.libraryManaging.dto.*;
import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.service.*;
import io.swagger.v3.oas.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Add a new book. Unique ISBN is required.")
    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody BookDto bookDto) throws IllegalGenreException {
        return new ResponseEntity<>(bookService.addBook(bookDto), HttpStatus.OK);
    }

    @Operation(summary = "Get book information by ISBN")
    @GetMapping("/getinfo/{isbn}")
    public ResponseEntity<?> getBookInfoByIsbn(@PathVariable String isbn) throws BookNotFoundException {
        return new ResponseEntity<>(bookService.getBookInfoByIsbn(isbn), HttpStatus.OK);
    }

    @Operation(summary = "Get book information by author")
    @GetMapping("/getInfoByAuthor/{author}")
    public ResponseEntity<?> getBooksInfoByAuthor(@PathVariable String author) throws BookNotFoundException{
        return new ResponseEntity<>(bookService.getBooksInfoByAuthor(author), HttpStatus.OK);
    }

    @Operation(summary = "Get book information by title")
    @GetMapping("/getInfoByTitle/{title}")
    public ResponseEntity<?> getBooksInfoByTitle(@PathVariable String title) throws BookNotFoundException{
        return new ResponseEntity<>(bookService.getBookInfoByTitle(title), HttpStatus.OK);
    }

    @Operation(summary = "Get all books")
    @GetMapping("/books")
    public ResponseEntity<?> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooksIsbnAsString(), HttpStatus.OK);
    }


}

