package com.accenture.libraryManaging.observer;

import com.accenture.libraryManaging.repository.entity.*;

public interface BookObserver {
    void update(String bookIsbn, String user);
}