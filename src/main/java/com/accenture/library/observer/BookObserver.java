package com.accenture.library.observer;

public interface BookObserver {
    void update(String bookIsbn, String user);
}