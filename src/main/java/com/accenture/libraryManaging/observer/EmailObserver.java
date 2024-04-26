//package com.accenture.libraryManaging.observer;
//
//import com.accenture.libraryManaging.email.*;
//import com.accenture.libraryManaging.repository.entity.*;
//import org.springframework.beans.factory.annotation.*;
//import org.springframework.stereotype.*;
//
//@Component
//public class EmailObserver implements BookObserver {
//    private EmailClient emailClient;
//
//    @Autowired
//    public EmailObserver(EmailClient emailClient) {
//        this.emailClient = emailClient;
//    }
//
//    @Override
//    public void update(Book book, User user) {
////        String subject = "Книга возвращена";
////        String body = "Пользователь " + user.getName() + " вернул книгу " + book.getTitle();
////        emailClient.sendEmail("from@example.com", "to@example.com", subject, body);
//    }
//
//
//}