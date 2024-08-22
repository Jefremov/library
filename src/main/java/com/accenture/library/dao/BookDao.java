package com.accenture.library.dao;

import lombok.*;

import java.util.*;

@Data
public class BookDao {

    private String title;
    private String author;
    private int publishingYear;
    private String genre;
    private String isbn;
    private String description;
    private int available;
    private Set<String> users;
}
