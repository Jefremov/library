package com.accenture.libraryManaging.template;

import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.repository.entity.*;
import org.springframework.stereotype.*;

import java.util.*;


@Component
public class BorrowBookOperation implements BookOperation {
    @Override
    public void perform(Book book, User user, Set<String> books)
            throws BookNotAvailableException, BookAlreadyTakenException {

        if (book.getAvailable() <= 0) {
            throw new BookNotAvailableException("Book with isbn: " + book.getIsbn() + " not found");
        }
        if (books.contains(book.getIsbn())) {
            throw new BookAlreadyTakenException("Book with isbn: " + book.getIsbn() + " has already been borrowed by user " + user.getUsername());
        }
        book.setAvailable(book.getAvailable() - 1);
        Set<User> users = book.getUsers();
        users.add(user);
        book.setUsers(users);
        PopularityDecorator popularBook = new PopularityDecorator(book, user.getUsername());
        book.setDescription(popularBook.getDescription());

    }
}