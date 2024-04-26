package com.accenture.libraryManaging.service.impl;

import com.accenture.libraryManaging.email.*;
import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.repository.*;
import com.accenture.libraryManaging.repository.entity.*;
import com.accenture.libraryManaging.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
public class UserBookServiceImpl implements UserBookService {

    private BookRepository bookRepository;
    private UserBookRepository userBookRepository;
    private UserRepository userRepository;

    @Autowired
    public UserBookServiceImpl(BookRepository bookRepository, UserBookRepository userBookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userBookRepository = userBookRepository;
        this.userRepository = userRepository;
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

        if (!bookRepository.existsByIsbn(isbn)) {
            throw new BookNotFoundException("Book with isbn: " + isbn + " not found");
        }
        if (!userRepository.existsByUsername(username)) {
            throw new UserNotFoundException("User with username: " + username + " not found");
        }
        Book book = bookRepository.findByIsbn(isbn);
        User user = userRepository.findByUsername(username);
        Set<String> books = getBooksByUser(user);
        if (book.getAvailable() > 0) {
            if (books.contains(isbn)) {
                throw new BookAlreadyTakenException("Book with isbn: " + isbn + " has already been borrowed by user " + username);
            }
            book.setAvailable(book.getAvailable() - 1);
            Set<User> users = book.getUsers();
            users.add(user);
            book.setUsers(users);
            return bookRepository.save(book);
        }
        throw new BookNotAvailableException("Book with isbn: " + isbn + " is not available");
    }

    @Override
    public Book returnBook(String username, String isbn) throws BookNotFoundException, UserNotFoundException {
        if (!bookRepository.existsByIsbn(isbn)) {
            throw new BookNotFoundException("Book with isbn: " + isbn + " not found");
        }
        if (!userRepository.existsByUsername(username)) {
            throw new UserNotFoundException("User with username: " + username + " not found");
        }
        Book book = bookRepository.findByIsbn(isbn);
        User user = userRepository.findByUsername(username);

        if (user.getBooks().contains(book)) {
            book.setAvailable(book.getAvailable() + 1);
            Set<User> users = book.getUsers();
            users.remove(user);
            book.setUsers(users);

            EmailClient emailClient = EmailClient.getInstance("smtp.example.com", 587, "username", "password");
            String subject = "Subject";
            String body = "Body";
            emailClient.sendEmail("from@example.com", "to@example.com", subject, body);

            return bookRepository.save(book);
        }
        throw new BookNotFoundException("Book with isbn: " + isbn + " has not been borrowed by user " + username);

    }

    @Override
    public String orderBook(String username, String isbn) throws BookNotFoundException, UserNotFoundException, BookAlreadyTakenException {

        if (!bookRepository.existsByIsbn(isbn)) {
            throw new BookNotFoundException("Book with isbn: " + isbn + " not found");
        }
        if (!userRepository.existsByUsername(username)) {
            throw new UserNotFoundException("User with username: " + username + " not found");
        }
        Book book = bookRepository.findByIsbn(isbn);
        User user = userRepository.findByUsername(username);
        if(book.getAvailable() > 0){
            throw new BookAlreadyTakenException("Book with isbn: " + isbn + " is available");
        }
        if(user.getBooks().contains(book)){
            throw new BookAlreadyTakenException("Book with isbn: " + isbn + " has already been borrowed by user " + username);
        }
        Order order = new Order();



        return "Book ordered successfully";
    }


}
