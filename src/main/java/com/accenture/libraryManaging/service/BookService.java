package com.accenture.libraryManaging.service;

import com.accenture.libraryManaging.dao.*;
import com.accenture.libraryManaging.dto.*;
import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.repository.entity.*;

import java.util.*;

public interface BookService {

    Book addBook(BookDto bookDto) throws IllegalGenreException;

    BookDao getBookInfoByIsbn(String isbn) throws BookNotFoundException;

    List<BookDao> getBooksInfoByAuthor(String author) throws BookNotFoundException;

    BookDao getBookInfoByTitle(String title) throws BookNotFoundException;

}

