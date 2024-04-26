package com.accenture.libraryManaging.template;

import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.repository.entity.*;
import org.springframework.stereotype.*;

import java.util.*;


@Component
public class ReturnBookOperation implements BookOperation {
    @Override
    public void perform(Book book, User user, Set<String> books)
            throws BookNotFoundException {

        if (books.contains(book.getIsbn())) {
            book.setAvailable(book.getAvailable() + 1);
            Set<User> users = book.getUsers();
            users.remove(user);
            book.setUsers(users);

        } else {
            throw new BookNotFoundException("Book with isbn: " + book.getIsbn() + " has not been borrowed by user " + user.getUsername());
        }
    }
}