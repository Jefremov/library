package com.accenture.library.template;

import com.accenture.library.exceptions.*;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.repository.UserRepository;
import com.accenture.library.repository.entity.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ReturnBookOperationTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private ReturnBookOperation returnBookOperation;

    @Test
    void performThrowsBookNotFoundExceptionWhenBookIsNotBorrowedByUser() {
        // Given
        Book book = new Book();
        book.setIsbn("123");
        User user = new User();
        user.setUsername("username");

        // When / Then
        assertThrows(BookNotFoundException.class, () -> returnBookOperation.perform(book, user, userRepository, bookRepository));
    }

    @Test
    void performReturnsBookSuccessfullyWhenBookIsBorrowedByUser() {
        // Given

        Book book = new Book();
        book.setIsbn("123");
        book.setAvailable(0);
        User user = new User();
        user.setUsername("username");

        HashSet<String> books = new HashSet<>();
        books.add("123");
        user.setBookIsbns(books);

        HashSet<String> users = new HashSet<>();
        users.add(user.getUsername());
        book.setUsers(users);

        // When
        assertDoesNotThrow(() -> returnBookOperation.perform(book, user, userRepository, bookRepository));

        // Then
        assertEquals(1, book.getAvailable());
        assertFalse(book.getUsers().contains(user.getUsername()));
    }
}