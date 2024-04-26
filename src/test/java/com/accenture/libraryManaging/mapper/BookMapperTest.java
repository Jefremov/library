package com.accenture.libraryManaging.mapper;

import com.accenture.libraryManaging.dao.*;
import com.accenture.libraryManaging.dto.*;
import com.accenture.libraryManaging.repository.entity.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    @Test
    void mapToEntity() {

        BookMapper bookMapper = new BookMapper();
        BookDto bookDto = new BookDto();
        bookDto.setAuthor("Author");
        bookDto.setIsbn("ISBN");
        bookDto.setTitle("Title");
        bookDto.setPublishingYear(2020);
        bookDto.setDescription("Description");

        Book newBook = new Book();
        Book expectedBook = new Book();
        expectedBook.setAuthor("Author");
        expectedBook.setIsbn("ISBN");
        expectedBook.setTitle("Title");
        expectedBook.setPublishingYear(2020);
        expectedBook.setDescription("Description");
        expectedBook.setUsers(new HashSet<User>());

        Book mappedBook = bookMapper.mapToEntity(bookDto, newBook);

        assertEquals(expectedBook, mappedBook);
    }

    @Test
    void mapToDaoReturnsCorrectDaoWhenBookIsProvided() {
        BookMapper bookMapper = new BookMapper();
        Book book = new Book();
        book.setAuthor("Author");
        book.setIsbn("ISBN");
        book.setTitle("Title");
        book.setPublishingYear(2020);
        book.setDescription("Description");
        book.setAvailable(1);
        book.setGenre(Genre.FICTION);
        User user = new User();
        user.setUsername("User1");
        book.setUsers(new HashSet<>(Collections.singletonList(user)));

        BookDao expectedBookDao = new BookDao();
        expectedBookDao.setAuthor("Author");
        expectedBookDao.setIsbn("ISBN");
        expectedBookDao.setTitle("Title");
        expectedBookDao.setPublishingYear(2020);
        expectedBookDao.setDescription("Description");
        expectedBookDao.setAvailable(1);
        expectedBookDao.setGenre("FICTION");
        expectedBookDao.setUsers(new HashSet<>(Collections.singletonList("User1")));

        BookDao mappedBookDao = bookMapper.mapToDao(book);

        assertEquals(expectedBookDao, mappedBookDao);
    }

    @Test
    void mapToDaoReturnsCorrectDaoWhenBookHasNoUsers() {
        BookMapper bookMapper = new BookMapper();
        Book book = new Book();
        book.setAuthor("Author");
        book.setIsbn("ISBN");
        book.setTitle("Title");
        book.setPublishingYear(2020);
        book.setDescription("Description");
        book.setAvailable(1);
        book.setGenre(Genre.FICTION);

        BookDao expectedBookDao = new BookDao();
        expectedBookDao.setAuthor("Author");
        expectedBookDao.setIsbn("ISBN");
        expectedBookDao.setTitle("Title");
        expectedBookDao.setPublishingYear(2020);
        expectedBookDao.setDescription("Description");
        expectedBookDao.setAvailable(1);
        expectedBookDao.setGenre("FICTION");
        expectedBookDao.setUsers(new HashSet<>());

        BookDao mappedBookDao = bookMapper.mapToDao(book);

        assertEquals(expectedBookDao, mappedBookDao);
    }

}