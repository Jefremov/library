package com.accenture.libraryManaging.observer;

import com.accenture.libraryManaging.repository.*;
import com.accenture.libraryManaging.repository.entity.*;
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

    public BookPublisher(List<BookObserver> observers, OrderRepository orderRepository, UserRepository userRepository) {
        this.observers = observers;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }


    public void addObserver(BookObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(BookObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String bookIsbn) {
        Book book = bookRepository.findByIsbn(bookIsbn);
        List<Order> orders = orderRepository.findByBookId(book.getId());
        List<User> users = new ArrayList<>();
        for (Order order : orders) {
            users.add(userRepository.findById(order.getUserId()).get());
        }

        for (BookObserver observer : observers) {
            for (User user : users){
                observer.update(bookIsbn, user.getUsername());
            }
        }
    }
}