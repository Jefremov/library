package com.accenture.library.template;

import com.accenture.library.exceptions.*;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.repository.UserRepository;
import com.accenture.library.repository.entity.*;
import org.springframework.stereotype.*;

import java.util.*;


@Component
public class ReturnBookOperation implements BookOperation {
    @Override
    public void perform(Book book, User user, UserRepository userRepository, BookRepository bookRepository)
            throws BookNotFoundException {
        HashSet<String> books = user.getBookIsbns();

        if (books.contains(book.getIsbn())) {
            book.setAvailable(book.getAvailable() + 1);
            HashSet<String> users = book.getUsers();
            users.remove(user.getUsername());
            book.setUsers(users);

            HashSet<String> userBooks = user.getBookIsbns();
            userBooks.remove(book.getIsbn());
            user.setBookIsbns(userBooks);

            userRepository.save(user);
            bookRepository.save(book);


        } else {
            throw new BookNotFoundException("Book with isbn: " + book.getIsbn() + " has not been borrowed by user " + user.getUsername());
        }
    }
}