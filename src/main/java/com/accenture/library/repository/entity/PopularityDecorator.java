package com.accenture.library.repository.entity;

import java.util.Objects;

public class PopularityDecorator extends Book {

    private final Book book;
    private final String username;

    public PopularityDecorator(Book book, String username) {
        this.book = book;
        this.username = username;
    }

    @Override
    public String getDescription() {
        return book.getDescription() + "\nReader: " + username;
    }

    @Override
    public void setDescription(String description) {
        this.book.setDescription(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PopularityDecorator that = (PopularityDecorator) o;
        return Objects.equals(book, that.book) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), book, username);
    }
}
