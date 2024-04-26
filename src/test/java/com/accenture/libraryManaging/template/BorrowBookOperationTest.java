package com.accenture.libraryManaging.template;

import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.repository.entity.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BorrowBookOperationTest {


    @Test
    void performThrowsBookNotAvailableExceptionWhenBookIsNotAvailable() {
        BorrowBookOperation borrowBookOperation = new BorrowBookOperation();
        Book book = new Book();
        book.setIsbn("123");
        book.setAvailable(0);
        User user = new User();
        user.setUsername("username");
        Set<String> books = new HashSet<>();

        assertThrows(BookNotAvailableException.class, () -> borrowBookOperation.perform(book, user, books));
    }

    @Test
    void performThrowsBookAlreadyTakenExceptionWhenBookIsAlreadyTaken() {
        BorrowBookOperation borrowBookOperation = new BorrowBookOperation();
        Book book = new Book();
        book.setIsbn("123");
        book.setAvailable(1);
        User user = new User();
        user.setUsername("username");
        Set<String> books = new HashSet<>();
        books.add("123");

        assertThrows(BookAlreadyTakenException.class, () -> borrowBookOperation.perform(book, user, books));
    }

    @Test
    void performDecreasesAvailableBooksByOneWhenBookIsAvailable() throws BookAlreadyTakenException, BookNotAvailableException {
        BorrowBookOperation borrowBookOperation = new BorrowBookOperation();
        Book book = new Book();
        book.setIsbn("123");
        book.setAvailable(1);
        User user = new User();
        user.setUsername("username");
        Set<String> books = new HashSet<>();

        borrowBookOperation.perform(book, user, books);

        assertEquals(0, book.getAvailable());
    }

    @Test
    void performAddsUserToBookUsersWhenBookIsAvailable() throws BookAlreadyTakenException, BookNotAvailableException {
        BorrowBookOperation borrowBookOperation = new BorrowBookOperation();
        Book book = new Book();
        book.setIsbn("123");
        book.setAvailable(1);
        User user = new User();
        user.setUsername("username");
        Set<String> books = new HashSet<>();

        borrowBookOperation.perform(book, user, books);

        assertTrue(book.getUsers().contains(user));
    }


}