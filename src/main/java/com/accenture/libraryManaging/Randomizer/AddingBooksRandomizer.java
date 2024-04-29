package com.accenture.libraryManaging.Randomizer;

import com.accenture.libraryManaging.dto.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class AddingBooksRandomizer {

    private static final String ADD_BOOK_URL = "http://localhost:8080/books/add";
    private static final String[] GENRES = {"FICTION", "HISTORICAL", "SCIENTIFIC", "CHILDREN"};

    public static void addRandomBooks() {
        for (int i = 0; i < 20; i++) {
            addRandomBook();
        }
    }

    private static void addRandomBook() {

        RestTemplate restTemplate = new RestTemplate();

        BookDto bookDto = generateRandomBookDto();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BookDto> requestEntity = new HttpEntity<>(bookDto, headers);

        ResponseEntity<?> responseEntity = restTemplate.postForEntity(ADD_BOOK_URL, requestEntity, String.class);
    }

    private static BookDto generateRandomBookDto() {
        Random random = new Random();
        BookDto bookDto = new BookDto();
        String isbn = String.valueOf(random.nextInt(12));
        bookDto.setTitle("Book" + isbn);
        bookDto.setAuthor("Random Author" + + random.nextInt(6));
        bookDto.setPublishingYear(random.nextInt(2022 - 1900 + 1) + 1900); //
        bookDto.setGenre(getRandomGenre());
        bookDto.setIsbn("Book" + isbn); //
        bookDto.setDescription("Random Description" + + random.nextInt(1000));
        return bookDto;
    }

    private static String getRandomGenre() {
        Random random = new Random();
        int index = random.nextInt(GENRES.length);
        return GENRES[index];
    }
}
