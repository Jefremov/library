package com.accenture.library.template;

import com.accenture.library.exceptions.*;
import com.accenture.library.repository.entity.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ReturnBookOperationTest {


@Test
void performThrowsBookNotFoundExceptionWhenBookIsNotBorrowedByUser() {
    // Given
    ReturnBookOperation returnBookOperation = new ReturnBookOperation();
    Book book = new Book();
    book.setIsbn("123");
    User user = new User();
    user.setUsername("username");
    Set<String> books = new HashSet<>();

    // When / Then
    assertThrows(BookNotFoundException.class, () -> returnBookOperation.perform(book, user, books));
}

@Test
void performReturnsBookSuccessfullyWhenBookIsBorrowedByUser() {
    // Given
    ReturnBookOperation returnBookOperation = new ReturnBookOperation();
    Book book = new Book();
    book.setIsbn("123");
    book.setAvailable(0);
    User user = new User();
    user.setUsername("username");
    Set<String> books = new HashSet<>();
    books.add("123");

    // When
    assertDoesNotThrow(() -> returnBookOperation.perform(book, user, books));

    // Then
    assertEquals(1, book.getAvailable());
    assertFalse(book.getUsers().contains(user));
}
}