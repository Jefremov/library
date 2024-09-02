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
class BorrowBookOperationTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BorrowBookOperation borrowBookOperation;

    @Test
    void performThrowsBookNotAvailableExceptionWhenBookIsNotAvailable() {

        Book book = new Book();
        book.setIsbn("123");
        book.setAvailable(0);
        User user = new User();
        user.setUsername("username");

        assertThrows(BookNotAvailableException.class, () -> borrowBookOperation.perform(book, user, userRepository, bookRepository));
    }

    @Test
    void performThrowsBookAlreadyTakenExceptionWhenBookIsAlreadyTaken() {

        Book book = new Book();
        book.setIsbn("123");
        book.setAvailable(1);

        User user = new User();
        user.setUsername("username");
        HashSet<String> books = new HashSet<>();
        books.add("123");
        user.setBookIsbns(books);

        HashSet<String> users = new HashSet<>();
        users.add("username");
        book.setUsers(users);

        assertThrows(BookAlreadyTakenException.class, () -> borrowBookOperation.perform(book, user, userRepository, bookRepository));
    }



    @Test
    void performDecreasesAvailableBooksByOneWhenBookIsAvailable() throws BookAlreadyTakenException, BookNotAvailableException {

        Book book = new Book();
        book.setIsbn("123");
        book.setAvailable(1);
        User user = new User();
        user.setUsername("username");

        borrowBookOperation.perform(book, user, userRepository, bookRepository);

        assertEquals(0, book.getAvailable());
    }

    @Test
    void performAddsUserToBookUsersWhenBookIsAvailable() throws BookAlreadyTakenException, BookNotAvailableException {

        Book book = new Book();
        book.setIsbn("123");
        book.setAvailable(1);
        User user = new User();
        user.setUsername("username");

        borrowBookOperation.perform(book, user, userRepository, bookRepository);

        assertTrue(book.getUsers().contains(user.getUsername()));
    }


}