package com.accenture.libraryManaging.repository.entity;

import com.fasterxml.jackson.annotation.*;
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

    @ManyToMany
    @JoinTable(
            name = "user_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();


}
