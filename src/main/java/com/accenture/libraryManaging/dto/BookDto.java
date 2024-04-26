package com.accenture.libraryManaging.dto;

import lombok.*;

import java.util.*;

@Data
public class BookDto {

    private String title;
    private String author;
    private int publishingYear;
    private String genre;
    private String isbn;
    private String description;

}
