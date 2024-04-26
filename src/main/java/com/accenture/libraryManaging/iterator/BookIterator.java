package com.accenture.libraryManaging.iterator;

import com.accenture.libraryManaging.repository.entity.*;

public interface BookIterator {
    boolean hasNext();
    Book next();
}