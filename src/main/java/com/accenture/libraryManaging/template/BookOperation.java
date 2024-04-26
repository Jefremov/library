package com.accenture.libraryManaging.template;

import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.repository.entity.*;

import java.util.*;

public interface BookOperation {
    void perform(Book book, User user, Set<String> books)
            throws BookNotFoundException, BookNotAvailableException, UserNotFoundException, BookAlreadyTakenException;
}
