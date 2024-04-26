//package com.accenture.libraryManaging.observer;
//
//import com.accenture.libraryManaging.repository.entity.*;
//import org.springframework.stereotype.*;
//
//import java.util.*;
//
//@Component
//public class BookPublisher {
//    private List<BookObserver> observers = new ArrayList<>();
//
//    public void addObserver(BookObserver observer) {
//        observers.add(observer);
//    }
//
//    public void removeObserver(BookObserver observer) {
//        observers.remove(observer);
//    }
//
//    public void notifyObservers(Book book, User user) {
//        for (BookObserver observer : observers) {
//            observer.update(book, user);
//        }
//    }
//}