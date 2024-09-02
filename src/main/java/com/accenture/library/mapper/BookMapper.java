package com.accenture.library.mapper;


import com.accenture.library.dao.*;
import com.accenture.library.dto.*;
import com.accenture.library.repository.entity.*;
import org.springframework.stereotype.*;

import java.util.*;


@Component
public class BookMapper {

    public Book mapToEntity(BookDto bookDto, Book book) {
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setPublishingYear(bookDto.getPublishingYear());
        book.setDescription(bookDto.getDescription());
        return book;
    }

    public BookDao mapToDao(Book book) {
        BookDao bookDao = new BookDao();
        bookDao.setTitle(book.getTitle());
        bookDao.setAuthor(book.getAuthor());
        bookDao.setPublishingYear(book.getPublishingYear());
        bookDao.setGenre(String.valueOf(book.getGenre()));
        bookDao.setIsbn(book.getIsbn());
        bookDao.setDescription(book.getDescription());
        bookDao.setAvailable(book.getAvailable());
        Set<String> users = new HashSet<>(book.getUsers());
        bookDao.setUsers(users);
        return bookDao;
    }

}
