package com.accenture.libraryManaging.service.impl;


import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.observer.*;
import com.accenture.libraryManaging.repository.*;
import com.accenture.libraryManaging.repository.entity.*;
import com.accenture.libraryManaging.service.*;
import com.accenture.libraryManaging.template.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
public class UserBookServiceImpl implements UserBookService {

    private BookRepository bookRepository;
    private UserBookRepository userBookRepository;
    private UserRepository userRepository;
    private BookPublisher bookPublisher;
    private OrderRepository orderRepository;
    private BorrowBookOperation borrowBookOperation;
    private ReturnBookOperation returnBookOperation;


    @Autowired
    public UserBookServiceImpl(BookRepository bookRepository, UserBookRepository userBookRepository,
                               UserRepository userRepository, BookPublisher bookPublisher,
                               OrderRepository orderRepository, BorrowBookOperation borrowBookOperation,
                               ReturnBookOperation returnBookOperation) {
        this.bookRepository = bookRepository;
        this.userBookRepository = userBookRepository;
        this.userRepository = userRepository;
        this.bookPublisher = bookPublisher;
        this.orderRepository = orderRepository;
        this.borrowBookOperation = borrowBookOperation;
        this.returnBookOperation = returnBookOperation;
    }


    @Override
    public Set<String> getBooksByUser(User user) {
        List<UserBook> userBooks = userBookRepository.findByUserId(user.getId());
        Set<String> books = new HashSet<>();
        for (UserBook userBook : userBooks) {
            books.add(userBook.getBook().getIsbn());
        }
        return books;
    }

    @Override
    public Set<String> getUsersByBook(Book book) {
        List<UserBook> userBooks = userBookRepository.findByBookId(book.getId());
        Set<String> users = new HashSet<>();
        for (UserBook userBook : userBooks) {
            users.add(userBook.getUser().getUsername());
        }
        return users;
    }

    @Override
    @Transactional
    public Book getBook(String username, String isbn)
            throws BookNotFoundException, BookNotAvailableException, UserNotFoundException, BookAlreadyTakenException {
        checkBookAndUser(username, isbn);
        Book book = bookRepository.findByIsbn(isbn);
        User user = userRepository.findByUsername(username);
        Set<String> books = getBooksByUser(user);

        performBookOperation(borrowBookOperation, book, user, books, isbn, username);

        return book;
    }

    @Override
    public Book returnBook(String username, String isbn) throws BookNotFoundException, UserNotFoundException,
            BookAlreadyTakenException, BookNotAvailableException {

        checkBookAndUser(username, isbn);
        Book book = bookRepository.findByIsbn(isbn);
        User user = userRepository.findByUsername(username);
        Set<String> books = getBooksByUser(user);
        performBookOperation(returnBookOperation, book, user, books, isbn, username);
        UserBook userBook = userBookRepository.findByUserIdAndBookId(user.getId(), book.getId());
        userBookRepository.delete(userBook);
        bookPublisher.notifyObservers(isbn);
        return book;
    }

    @Override
    public String orderBook(String username, String isbn) throws BookNotFoundException, UserNotFoundException, BookAlreadyTakenException {

        checkBookAndUser(username, isbn);
        Book book = bookRepository.findByIsbn(isbn);
        User user = userRepository.findByUsername(username);
        if (orderRepository.existsByBookIdAndUserId(book.getId(), user.getId())) {
            throw new BookAlreadyTakenException("Book with isbn: " + isbn + " has already been borrowed by user " + username);
        }
        if (book.getAvailable() > 0) {
            throw new BookAlreadyTakenException("Book with isbn: " + isbn + " is available");
        }
        if (user.getBooks().contains(book)) {
            throw new BookAlreadyTakenException("Book with isbn: " + isbn + " has already been borrowed by user " + username);
        }
        Order order = new Order();
        order.setUserId(user.getId());
        order.setBookId(book.getId());
        orderRepository.save(order);
        return "Book ordered successfully";
    }

    protected void checkBookAndUser(String username, String isbn) throws BookNotFoundException, UserNotFoundException {
        if (!bookRepository.existsByIsbn(isbn)) {
            throw new BookNotFoundException("Book with isbn: " + isbn + " not found");
        }
        if (!userRepository.existsByUsername(username)) {
            throw new UserNotFoundException("User with username: " + username + " not found");
        }
    }

    protected void performBookOperation(BookOperation operation, Book book, User user, Set<String> books, String isbn, String username)
            throws UserNotFoundException, BookAlreadyTakenException, BookNotFoundException, BookNotAvailableException {

        operation.perform(book, user, books);
        bookRepository.save(book);
    }

}
