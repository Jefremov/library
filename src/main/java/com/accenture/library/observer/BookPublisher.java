package com.accenture.library.observer;

import com.accenture.library.repository.*;
import com.accenture.library.repository.entity.*;
import jakarta.transaction.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class BookPublisher {
    private List<BookObserver> observers;
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private BookRepository bookRepository;

    @Autowired
    public BookPublisher(List<BookObserver> observers, OrderRepository orderRepository,
                         UserRepository userRepository, BookRepository bookRepository) {
        this.observers = observers;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public void addObserver(BookObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(BookObserver observer) {
        observers.remove(observer);
    }

    @Transactional
    public void notifyObservers(String bookIsbn) {
        Book book = bookRepository.findByIsbn(bookIsbn);
        List<Order> orders = orderRepository.findByBookId(book.getId());
        List<User> users = new ArrayList<>();
        for (Order order : orders) {
            users.add(userRepository.findById(order.getUserId())
                    .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + order.getUserId())));
        }

        for (BookObserver observer : observers) {
            for (User user : users){
                observer.update(bookIsbn, user.getUsername());
            }
        }
        orderRepository.deleteByBookId(book.getId());
    }
}