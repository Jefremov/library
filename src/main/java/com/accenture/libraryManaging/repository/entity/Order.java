package com.accenture.libraryManaging.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "book_id")
    private Long bookId;
}
