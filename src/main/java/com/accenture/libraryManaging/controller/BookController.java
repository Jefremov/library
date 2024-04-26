package com.accenture.libraryManaging.controller;


import com.accenture.libraryManaging.dto.*;
import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.service.*;
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

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody BookDto bookDto) throws IllegalGenreException {
        return new ResponseEntity<>(bookService.addBook(bookDto), HttpStatus.OK);
    }

    @GetMapping("/getinfo/{isbn}")
    public ResponseEntity<?> getBookInfoByIsbn(@PathVariable String isbn) throws BookNotFoundException {
        return new ResponseEntity<>(bookService.getBookInfoByIsbn(isbn), HttpStatus.OK);
    }

    @GetMapping("/getInfoByAuthor/{author}")
    public ResponseEntity<?> getBooksInfoByAuthor(@PathVariable String author) throws BookNotFoundException{
        return new ResponseEntity<>(bookService.getBooksInfoByAuthor(author), HttpStatus.OK);
    }

    @GetMapping("/getInfoByTitle/{title}")
    public ResponseEntity<?> getBooksInfoByTitle(@PathVariable String title) throws BookNotFoundException{
        return new ResponseEntity<>(bookService.getBookInfoByTitle(title), HttpStatus.OK);
    }

    @GetMapping("/books")
    public ResponseEntity<?> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooksIsbnAsString(), HttpStatus.OK);
    }


}

