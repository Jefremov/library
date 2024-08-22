package com.accenture.libraryManaging.controller;

import com.accenture.libraryManaging.dto.BookDto;
import com.accenture.libraryManaging.factory.BookFactory;
import com.accenture.libraryManaging.mapper.BookMapper;
import com.accenture.libraryManaging.repository.BookRepository;
import com.accenture.libraryManaging.repository.UserRepository;
import com.accenture.libraryManaging.repository.entity.Book;
import com.accenture.libraryManaging.service.BookService;
import com.accenture.libraryManaging.service.UserBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookControllerIntegrationTest {


    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookFactory bookFactory;
    @Autowired
    private UserBookService userBookService;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void addBook(){
        BookDto bookDto = new BookDto();
        bookDto.setTitle("testBook");
        bookDto.setAuthor("testAuthor");
        bookDto.setPublishingYear(2024);
        bookDto.setGenre("CHILDREN");
        bookDto.setIsbn("testIsbn");
        bookDto.setDescription("testDescription");

        List<Book> bookList = bookRepository.findAll();
        assertEquals(bookList.size(), 0);

        ResponseEntity<?> response = restTemplate.postForEntity("/books/add", bookDto, BookDto.class);
        bookList = bookRepository.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookList.size(), 1);
        assertEquals("testBook", bookList.get(0).getTitle());
        assertEquals("testAuthor", bookList.get(0).getAuthor());
        assertEquals(2024, bookList.get(0).getPublishingYear());
        assertEquals("CHILDREN", bookList.get(0).getGenre().toString());
        assertEquals("testIsbn", bookList.get(0).getIsbn());
        assertEquals("testDescription", bookList.get(0).getDescription());
    }
}
