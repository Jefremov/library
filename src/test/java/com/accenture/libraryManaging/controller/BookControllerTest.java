package com.accenture.libraryManaging.controller;

import com.accenture.libraryManaging.dao.*;
import com.accenture.libraryManaging.dto.BookDto;
import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.repository.entity.*;
import com.accenture.libraryManaging.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BookControllerTest {

    private BookController bookController;
    private BookService bookService;
    private BookDto bookDto;
    private BookDao bookDao;
    private Book book;

    @BeforeEach
    void setUp() {
        bookService = Mockito.mock(BookService.class);
        bookDto = Mockito.mock(BookDto.class);
        bookController = new BookController(bookService);
        bookDao = Mockito.mock(BookDao.class);
        book = Mockito.mock(Book.class);
    }

    @Test
    void addBookReturnsNotNull() throws IllegalGenreException {
        Mockito.when(bookService.addBook(bookDto)).thenReturn(book);
        ResponseEntity<?> response = bookController.addBook(bookDto);
        assertNotNull(response);
    }

    @Test
    void addBookReturnsCorrectObject() throws IllegalGenreException {
        Mockito.when(bookService.addBook(bookDto)).thenReturn(book);
        ResponseEntity<?> response = bookController.addBook(bookDto);
        assertEquals(book, response.getBody());
    }

    @Test
    void addBookReturnsInstanceOfResponseEntity() throws IllegalGenreException {
        Mockito.when(bookService.addBook(bookDto)).thenReturn(book);
        ResponseEntity<?> response = bookController.addBook(bookDto);
        assertTrue(response instanceof ResponseEntity);
    }

    @Test
    void addBookReturnsStatusOk() throws IllegalGenreException {
        Mockito.when(bookService.addBook(bookDto)).thenReturn(book);
        ResponseEntity<?> response = bookController.addBook(bookDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void addBookReturnsCorrectBody() throws IllegalGenreException {
        Mockito.when(bookService.addBook(bookDto)).thenReturn(book);
        ResponseEntity<?> response = bookController.addBook(bookDto);
        assertEquals(book, response.getBody());
    }

    @Test
    void addBookThrowsIllegalGenreExceptionWhenGenreIsInvalid() throws IllegalGenreException {
        Mockito.when(bookService.addBook(bookDto)).thenThrow(new IllegalGenreException("Invalid genre"));
        Exception exception = assertThrows(IllegalGenreException.class, () -> bookController.addBook(bookDto));
        assertEquals("Invalid genre", exception.getMessage());
    }

    @Test
    void addBookDoesNotReturnWhenGenreIsInvalid() throws IllegalGenreException {
        Mockito.when(bookService.addBook(bookDto)).thenThrow(new IllegalGenreException("Invalid genre"));
        Exception exception = assertThrows(IllegalGenreException.class, () -> bookController.addBook(bookDto));
        assertNull(exception.getCause());
    }

    @Test
    void getBookInfoByIsbnReturnsStatusOkWhenBookExists() throws BookNotFoundException {
        String isbn = "1234567890";
        Mockito.when(bookService.getBookInfoByIsbn(isbn)).thenReturn(bookDao);
        ResponseEntity<?> response = bookController.getBookInfoByIsbn(isbn);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getBookInfoByIsbnThrowsBookNotFoundExceptionWhenBookDoesNotExist() throws BookNotFoundException {
        String isbn = "1234567890";
        Mockito.when(bookService.getBookInfoByIsbn(isbn)).thenThrow(new BookNotFoundException("Book with isbn: " + isbn + " not found"));
        Exception exception = assertThrows(BookNotFoundException.class, () -> bookController.getBookInfoByIsbn(isbn));
        assertEquals("Book with isbn: " + isbn + " not found", exception.getMessage());
    }

    @Test
    void getBooksInfoByAuthorReturnsCorrectBooksWhenBooksExist() throws BookNotFoundException {
        String author = "Author";
        List<BookDao> books = Arrays.asList(new BookDao(), new BookDao());
        Mockito.when(bookService.getBooksInfoByAuthor(author)).thenReturn(books);
        ResponseEntity<?> response = bookController.getBooksInfoByAuthor(author);
        assertEquals(books, response.getBody());
    }

    @Test
    void getBooksInfoByAuthorReturnsStatusOkWhenBooksExist() throws BookNotFoundException {
        String author = "Author";
        List<BookDao> books = Arrays.asList(new BookDao(), new BookDao());
        Mockito.when(bookService.getBooksInfoByAuthor(author)).thenReturn(books);
        ResponseEntity<?> response = bookController.getBooksInfoByAuthor(author);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getBooksInfoByAuthorThrowsBookNotFoundExceptionWhenBooksDoNotExist() throws BookNotFoundException {
        String author = "Author";
        Mockito.when(bookService.getBooksInfoByAuthor(author)).thenThrow(new BookNotFoundException("Books by author: " + author + " not found"));
        Exception exception = assertThrows(BookNotFoundException.class, () -> bookController.getBooksInfoByAuthor(author));
        assertEquals("Books by author: " + author + " not found", exception.getMessage());
    }

    @Test
    void getBooksInfoByTitleReturnsCorrectBookWhenBookExists() throws BookNotFoundException {
        String title = "Title";
        BookDao book = new BookDao();
        Mockito.when(bookService.getBookInfoByTitle(title)).thenReturn(book);
        ResponseEntity<?> response = bookController.getBooksInfoByTitle(title);
        assertEquals(book, response.getBody());
    }

    @Test
    void getBooksInfoByTitleReturnsStatusOkWhenBookExists() throws BookNotFoundException {
        String title = "Title";
        BookDao book = new BookDao();
        Mockito.when(bookService.getBookInfoByTitle(title)).thenReturn(book);
        ResponseEntity<?> response = bookController.getBooksInfoByTitle(title);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getBooksInfoByTitleThrowsBookNotFoundExceptionWhenBookDoesNotExist() throws BookNotFoundException {
        String title = "Title";
        Mockito.when(bookService.getBookInfoByTitle(title)).thenThrow(new BookNotFoundException("Book with title: " + title + " not found"));
        Exception exception = assertThrows(BookNotFoundException.class, () -> bookController.getBooksInfoByTitle(title));
        assertEquals("Book with title: " + title + " not found", exception.getMessage());
    }

    @Test
    void getAllBooksReturnsCorrectBooksWhenBooksExist() {
        String books = "1234567890\n0987654321";
        Mockito.when(bookService.getAllBooksIsbnAsString()).thenReturn(books);
        ResponseEntity<?> response = bookController.getAllBooks();
        assertEquals(books, response.getBody());
    }

    @Test
    void getAllBooksReturnsStatusOkWhenBooksExist() {
        String books = "1234567890\n0987654321";
        Mockito.when(bookService.getAllBooksIsbnAsString()).thenReturn(books);
        ResponseEntity<?> response = bookController.getAllBooks();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAllBooksReturnsEmptyListWhenNoBooksExist() {
        String books = "";
        Mockito.when(bookService.getAllBooksIsbnAsString()).thenReturn(books);
        ResponseEntity<?> response = bookController.getAllBooks();
        assertEquals(response.getBody(), "");
    }

}
