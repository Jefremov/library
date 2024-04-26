package com.accenture.libraryManaging.iterator;

import com.accenture.libraryManaging.repository.entity.*;

import java.util.*;

public class BookListIterator implements BookIterator {
    private List<Book> books;
    private int position = 0;

    public BookListIterator(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean hasNext() {
        return position < books.size();
    }

    @Override
    public Book next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Book book = books.get(position);
        position++;
        return book;
    }
}