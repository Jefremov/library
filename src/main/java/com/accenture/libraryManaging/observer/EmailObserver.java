package com.accenture.libraryManaging.observer;

import com.accenture.libraryManaging.email.*;
import com.accenture.libraryManaging.repository.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class EmailObserver implements BookObserver {

    @Override
    public void update(String isbn, String username) {
        EmailClient emailClient = EmailClient.getInstance("smtp.example.com", 25, "username", "password");
        String subject = "subject";
        String body = "Book " + isbn + " is available for you";
        emailClient.sendEmail("from@example.com", "to@example.com", subject, body);
        System.out.println("Book " + isbn + " is available for you");
    }
}