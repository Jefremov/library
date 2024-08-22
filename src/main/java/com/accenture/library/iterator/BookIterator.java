package com.accenture.library.iterator;

import com.accenture.library.repository.entity.*;

public interface BookIterator {
    boolean hasNext();
    Book next();
}