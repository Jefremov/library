package com.accenture.library.template;

import com.accenture.library.exceptions.*;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.repository.UserRepository;
import com.accenture.library.repository.entity.*;
import org.springframework.stereotype.*;

import java.util.*;


@Component
public class BorrowBookOperation implements BookOperation {
    @Override
    public void perform(Book book, User user, UserRepository userRepository, BookRepository bookRepository)
            throws BookNotAvailableException, BookAlreadyTakenException {

        if (book.getAvailable() <= 0) {
            throw new BookNotAvailableException("Book with isbn: " + book.getIsbn() + " not available");
        }
        Set<String> userBooks = user.getBookIsbns();
        if (userBooks.contains(book.getIsbn())) {
            throw new BookAlreadyTakenException("Book with isbn: " + book.getIsbn() + " has already been borrowed by user " + user.getUsername());
        }

        book.getUsers().add(user.getUsername());
        user.getBookIsbns().add(book.getIsbn());
        book.setAvailable(book.getAvailable() - 1);
        PopularityDecorator popularBook = new PopularityDecorator(book, user.getUsername());
        book.setDescription(popularBook.getDescription());

        userRepository.save(user);
        bookRepository.save(book);

    }
}