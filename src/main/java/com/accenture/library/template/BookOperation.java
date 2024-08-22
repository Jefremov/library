package com.accenture.library.template;

import com.accenture.library.exceptions.*;
import com.accenture.library.repository.entity.*;

import java.util.*;

public interface BookOperation {
    void perform(Book book, User user, Set<String> books)
            throws BookNotFoundException, BookNotAvailableException, UserNotFoundException, BookAlreadyTakenException;
}
