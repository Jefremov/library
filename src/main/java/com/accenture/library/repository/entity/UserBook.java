package com.accenture.library.repository.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "user_book")
@Data
public class UserBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

}
