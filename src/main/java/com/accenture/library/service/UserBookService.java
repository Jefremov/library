package com.accenture.library.service;

import com.accenture.library.exceptions.*;
import com.accenture.library.repository.entity.*;

import java.util.*;

public interface UserBookService {
    Set<String> getBooksByUser(User user);

    Set<String> getUsersByBook(Book book);

    Book getBook(String username, String isbn)
            throws BookNotFoundException, BookNotAvailableException, UserNotFoundException, BookAlreadyTakenException;

    Book returnBook(String username, String isbn)
            throws BookNotFoundException, UserNotFoundException, BookAlreadyTakenException, BookNotAvailableException;

    String orderBook(String username, String isbn)
            throws BookNotFoundException, UserNotFoundException, BookAlreadyTakenException, BookNotAvailableException;
}
