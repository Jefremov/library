package com.accenture.libraryManaging.Randomizer;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class GetInfoRandomizer {

    private static final String GET_USER_INFO_URL = "http://localhost:8080/users/";
    private static final String GET_BOOK_INFO_URL = "http://localhost:8080/books/";

    public static void getUsersInfo() {
        for (int i = 0; i < 20; i++) {
            getUserInfo();
        }
    }

    private static void getUserInfo() {
        Random random = new Random();
        RestTemplate restTemplate = new RestTemplate();
        String username = "penguin" + random.nextInt(20);
        String url = GET_USER_INFO_URL + "getInfo/" + username;
        ResponseEntity<?> responseEntity = restTemplate.getForEntity(url, String.class);
    }

    public static void getBooksInfoByIsbn() {
        for (int i = 0; i < 20; i++) {
            getBookInfoByIsbn();
        }
    }

    private static void getBookInfoByIsbn() {
        Random random = new Random();
        RestTemplate restTemplate = new RestTemplate();
        String isbn = "Book" + random.nextInt(20);
        String url = GET_BOOK_INFO_URL + "getinfo/" + isbn;
        ResponseEntity<?> responseEntity = restTemplate.getForEntity(url, String.class);
    }

    public static void getBooksInfoByAuthor() {
        for (int i = 0; i < 20; i++) {
            getBookInfoByAuthor();
        }
    }

    private static void getBookInfoByAuthor() {
        Random random = new Random();
        RestTemplate restTemplate = new RestTemplate();
        String author = "Random Author" + random.nextInt(20);
        String url = GET_BOOK_INFO_URL + "getInfoByAuthor/" + author;
        ResponseEntity<?> responseEntity = restTemplate.getForEntity(url, String.class);
    }

    public static void getBooksInfoByTitle() {
        for (int i = 0; i < 20; i++) {
            getBookInfoByTitle();
        }
    }

    private static void getBookInfoByTitle() {
        Random random = new Random();
        RestTemplate restTemplate = new RestTemplate();
        String title = "Book" + random.nextInt(20);
        String url = GET_BOOK_INFO_URL + "getInfoByTitle/" + title;
        ResponseEntity<?> responseEntity = restTemplate.getForEntity(url, String.class);
    }

    public static void getAllBooksInfo() {
        RestTemplate restTemplate = new RestTemplate();
        String url = GET_BOOK_INFO_URL + "books";
        ResponseEntity<?> responseEntity = restTemplate.getForEntity(url, String.class);
    }
}
