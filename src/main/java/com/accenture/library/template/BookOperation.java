package com.accenture.library.template;

import com.accenture.library.exceptions.*;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.repository.UserRepository;
import com.accenture.library.repository.entity.*;


public interface BookOperation {
    void perform(Book book, User user, UserRepository userRepository, BookRepository bookRepository)
            throws BookNotFoundException, BookNotAvailableException, UserNotFoundException, BookAlreadyTakenException;
}
