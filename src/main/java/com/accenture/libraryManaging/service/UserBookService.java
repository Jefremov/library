package com.accenture.libraryManaging.service;

import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.repository.entity.*;

import java.util.*;

public interface UserBookService {
//    Set<String> getBooksByUser(User user);

    Set<String> getUsersByBook(Book book);

    Book getBook(String username, String isbn) throws BookNotFoundException, BookNotAvailableException, UserNotFoundException, BookAlreadyTakenException;;

    Book returnBook(String username, String isbn) throws BookNotFoundException, UserNotFoundException;

    String orderBook(String username, String isbn) throws BookNotFoundException, UserNotFoundException, BookAlreadyTakenException;
}
