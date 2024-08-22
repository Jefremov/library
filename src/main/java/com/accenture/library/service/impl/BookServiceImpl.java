package com.accenture.library.service.impl;

import com.accenture.library.dao.*;
import com.accenture.library.dto.*;
import com.accenture.library.exceptions.*;
import com.accenture.library.factory.*;
import com.accenture.library.iterator.*;
import com.accenture.library.mapper.*;
import com.accenture.library.repository.*;
import com.accenture.library.repository.entity.*;
import com.accenture.library.service.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookFactory bookFactory;
    private final BookMapper bookMapper;
    private final UserBookService userBookService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookFactory bookFactory,
                           BookMapper bookMapper, UserBookService userBookService) {
        this.bookRepository = bookRepository;
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
        return bookRepository.save(book);
    }

    @Override
    public BookDao getBookInfoByIsbn(String isbn) throws BookNotFoundException {

        if (!bookRepository.existsByIsbn(isbn)) {
            throw new BookNotFoundException("Book with isbn: " + isbn);
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
            throw new BookNotFoundException("Books by author: " + author);
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
            throw new BookNotFoundException("Book with title: " + title);
        }
        BookDao bookDao = bookMapper.mapToDao(book);
        bookDao.setUsers(userBookService.getUsersByBook(book));
        return bookDao;
    }

    @Override
    public String getAllBooksIsbnAsString() {

        List<Book> books = bookRepository.findAll();
        BookListIterator iterator = new BookListIterator(books);
        StringJoiner joiner = new StringJoiner("\n");
        while (iterator.hasNext()) {
            Book book = iterator.next();
            joiner.add(book.getTitle());
        }

        return joiner.toString();
    }


}
