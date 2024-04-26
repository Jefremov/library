package com.accenture.libraryManaging.repository.entity;

public class PopularityDecorator extends Book {

    private Book book;
    private String username;

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
}
