package com.accenture.libraryManaging.controller;

import com.accenture.libraryManaging.dto.*;
import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.repository.entity.*;
import com.accenture.libraryManaging.service.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

class UserBookControllerTest {


    private UserBookController userBookController;
    private UserBookService userBookService;
    private BookDto bookDto;
    private Book book;

    @BeforeEach
    void setUp() {
        userBookService = Mockito.mock(UserBookService.class);
        bookDto = Mockito.mock(BookDto.class);
        userBookController = new UserBookController(userBookService);
        book = Mockito.mock(Book.class);
    }


    @Test
    void getBookByIsbnReturnsCorrectBookWhenBookExistsAndIsAvailable() throws BookNotFoundException, BookNotAvailableException, UserNotFoundException, BookAlreadyTakenException {
        String isbn = "1234567890";
        String username = "username";
        Mockito.when(userBookService.getBook(username, isbn)).thenReturn(book);
        ResponseEntity<?> response = userBookController.getBook(username, isbn);
        assertEquals(book, response.getBody());
    }

    @Test
    void getBookByIsbnReturnsStatusOkWhenBookExistsAndIsAvailable()
            throws BookNotFoundException, BookNotAvailableException, UserNotFoundException, BookAlreadyTakenException {
        String isbn = "1234567890";
        String username = "username";
        Mockito.when(userBookService.getBook(username, isbn)).thenReturn(book);
        ResponseEntity<?> response = userBookController.getBook(username, isbn);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getBookByIsbnThrowsBookNotFoundExceptionWhenBookDoesNotExist()
            throws BookNotFoundException, BookNotAvailableException, UserNotFoundException, BookAlreadyTakenException {
        String isbn = "1234567890";
        String username = "username";
        Mockito.when(userBookService.getBook(username, isbn)).thenThrow(new BookNotFoundException("Book with isbn: " + isbn + " not found"));
        Exception exception = assertThrows(BookNotFoundException.class, () -> userBookController.getBook(username, isbn));
        assertEquals("Book with isbn: " + isbn + " not found", exception.getMessage());
    }

    @Test
    void getBookByIsbnThrowsBookNotAvailableExceptionWhenBookIsNotAvailable()
            throws BookNotFoundException, BookNotAvailableException, UserNotFoundException, BookAlreadyTakenException {
        String isbn = "1234567890";
        String username = "username";
        Mockito.when(userBookService.getBook(username, isbn)).thenThrow(new BookNotAvailableException("Book with isbn: " + isbn + " is not available"));
        Exception exception = assertThrows(BookNotAvailableException.class, () -> userBookController.getBook(username, isbn));
        assertEquals("Book with isbn: " + isbn + " is not available", exception.getMessage());
    }

    @Test
    void returnBookReturnsBookWhenBookExistsAndIsReturnedSuccessfully() throws BookNotFoundException, UserNotFoundException {
        String isbn = "1234567890";
        String username = "username";
        Mockito.when(userBookService.returnBook(username, isbn)).thenReturn(book);
        ResponseEntity<?> response = userBookController.returnBook(username, isbn);
        assertEquals(book, response.getBody());
    }

    @Test
    void returnBookReturnsStatusOkWhenBookExistsAndIsReturnedSuccessfully() throws BookNotFoundException, UserNotFoundException {
        String isbn = "1234567890";
        String username = "username";
        Mockito.when(userBookService.returnBook(username, isbn)).thenReturn(book);
        ResponseEntity<?> response = userBookController.returnBook(username, isbn);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void returnBookThrowsBookNotFoundExceptionWhenBookDoesNotExist() throws BookNotFoundException, UserNotFoundException {
        String isbn = "1234567890";
        String username = "username";
        Mockito.when(userBookService.returnBook(username, isbn)).thenThrow(new BookNotFoundException("Book with isbn: " + isbn + " not found"));
        Exception exception = assertThrows(BookNotFoundException.class, () -> userBookController.returnBook(username, isbn));
        assertEquals("Book with isbn: " + isbn + " not found", exception.getMessage());
    }

    @Test
    void returnBookThrowsUserNotFoundExceptionWhenUserDoesNotExist() throws BookNotFoundException, UserNotFoundException {
        String isbn = "1234567890";
        String username = "username";
        Mockito.when(userBookService.returnBook(username, isbn)).thenThrow(new UserNotFoundException("User with username: " + username + " not found"));
        Exception exception = assertThrows(UserNotFoundException.class, () -> userBookController.returnBook(username, isbn));
        assertEquals("User with username: " + username + " not found", exception.getMessage());
    }

    @Test
    void returnBookThrowsBookAlreadyTakenExceptionWhenBookIsAlreadyTaken() throws BookNotFoundException, UserNotFoundException {
        String isbn = "1234567890";
        String username = "username";
        Mockito.when(userBookService.returnBook(username, isbn)).thenThrow(new BookNotFoundException("Book with isbn: " + isbn + " is already taken"));
        Exception exception = assertThrows(BookNotFoundException.class, () -> userBookController.returnBook(username, isbn));
        assertEquals("Book with isbn: " + isbn + " is already taken", exception.getMessage());
    }

}