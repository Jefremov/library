package com.accenture.libraryManaging.Randomizer;

import org.springframework.http.*;
import org.springframework.web.client.*;

import java.util.*;


public class LibraryRandomizer {

    private static final String baseUrl = "http://localhost:8080/";

    public static void takeRandomBooks() {
        for (int i = 0; i < 50; i++) {
            takeBook();
        }
    }
    private static void takeBook(){

        Random random = new Random();
        RestTemplate restTemplate = new RestTemplate();
        String username = "penguin" + random.nextInt(20);
        String isbn = "Book" + random.nextInt(20);
        String url = baseUrl + "getBook/" + username + "/" + isbn;
        ResponseEntity<?> responseEntity = restTemplate.postForEntity(url, null, String.class);
    }

    public static void orderRandomBooks() {
        for (int i = 0; i < 50; i++) {
            orderBook();
        }

    }

    private static void orderBook() {
        Random random = new Random();
        RestTemplate restTemplate = new RestTemplate();
        String username = "penguin" + random.nextInt(20);
        String isbn = "Book" + random.nextInt(20);
        String url = baseUrl + "orderBook/" + username + "/" + isbn;
        ResponseEntity<?> responseEntity = restTemplate.postForEntity(url, null, String.class);
    }

    public static void returnRandomBooks() {
        for (int i = 0; i < 20; i++) {
            returnBook();
        }
    }

    private static void returnBook() {
        Random random = new Random();
        RestTemplate restTemplate = new RestTemplate();
        String username = "penguin" + random.nextInt(20);
        String isbn = "Book" + random.nextInt(20);
        String url = baseUrl + "returnBook/" + username + "/" + isbn;
        ResponseEntity<?> responseEntity = restTemplate.postForEntity(url, null, String.class);
    }

}
