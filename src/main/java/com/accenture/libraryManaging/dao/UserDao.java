package com.accenture.libraryManaging.dao;

import lombok.*;

import java.util.*;

@Data
public class UserDao {

    private String name;
    private Set<String> books;
}
