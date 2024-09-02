package com.accenture.library.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "isbn"), name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "genre")
    private Genre genre;

    @Column(name = "publishingYear")
    private int publishingYear;

    @Column(name = "available")
    private Integer available;

    @Column(name = "description")
    private String description;

    @Column(name = "isbn")
    private String isbn;

    private HashSet<String> users = new HashSet<>();


}
