package com.accenture.libraryManaging.service.impl;

import com.accenture.libraryManaging.dao.*;
import com.accenture.libraryManaging.dto.*;
import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.factory.*;
import com.accenture.libraryManaging.mapper.*;
import com.accenture.libraryManaging.repository.*;
import com.accenture.libraryManaging.repository.entity.*;
import com.accenture.libraryManaging.service.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private BookFactory bookFactory;
    private BookMapper bookMapper;
    private UserBookService userBookService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, UserRepository userRepository, BookFactory bookFactory, BookMapper bookMapper, UserBookService userBookService) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookFactory = bookFactory;
        this.bookMapper = bookMapper;
        this.userBookService = userBookService;
    }


    @Override
    public Book addBook(BookDto bookDto) throws IllegalGenreException {

        Book book = bookFactory.createBook(bookDto);
        book = bookMapper.mapToEntity(bookDto, book);
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            book = bookRepository.findByIsbn(book.getIsbn());
            book.setAvailable(book.getAvailable() + 1);
        } else {
            book.setAvailable(1);
        }
        Set<User> users = new HashSet<>();
        book.setUsers(users);
        Book savedBook = bookRepository.save(book);
        return savedBook;
    }

    @Override
    public BookDao getBookInfoByIsbn(String isbn) throws BookNotFoundException {

        if (!bookRepository.existsByIsbn(isbn)) {
            throw new BookNotFoundException("Book with isbn: " + isbn + " not found");
        }
        Book book = bookRepository.findByIsbn(isbn);
        BookDao bookDao = bookMapper.mapToDao(book);
        bookDao.setUsers(userBookService.getUsersByBook(book));
        return bookDao;
    }

    @Override
    public List<BookDao> getBooksInfoByAuthor(String author) throws BookNotFoundException {
        List<Book> books = bookRepository.findByAuthor(author);
        if (books.isEmpty()) {
            throw new BookNotFoundException("Books by author: " + author + " not found");
        }
        List<BookDao> bookDaos = new ArrayList<>();
        for (Book book : books) {
            BookDao bookDao = bookMapper.mapToDao(book);
            bookDao.setUsers(userBookService.getUsersByBook(book));
            bookDaos.add(bookDao);
        }
        return bookDaos;
    }

    @Override
    public BookDao getBookInfoByTitle(String title) throws BookNotFoundException {
        Book book = bookRepository.findByTitle(title);
        if (book == null) {
            throw new BookNotFoundException("Book with title: " + title + " not found");
        }
        BookDao bookDao = bookMapper.mapToDao(book);
        bookDao.setUsers(userBookService.getUsersByBook(book));
        return bookDao;
    }


}
