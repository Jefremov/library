package com.accenture.library.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<Order> orders;

    private HashSet<String> bookIsbns = new HashSet<>();

}
