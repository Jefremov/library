package com.accenture.library.service;

import com.accenture.library.dao.*;
import com.accenture.library.dto.*;
import com.accenture.library.exceptions.*;
import com.accenture.library.repository.entity.*;

import java.util.*;

public interface BookService {

    Book addBook(BookDto bookDto) throws IllegalGenreException;

    BookDao getBookInfoByIsbn(String isbn) throws BookNotFoundException;

    List<BookDao> getBooksInfoByAuthor(String author) throws BookNotFoundException;

    BookDao getBookInfoByTitle(String title) throws BookNotFoundException;

    String getAllBooksIsbnAsString();
}

