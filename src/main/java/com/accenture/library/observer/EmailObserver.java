package com.accenture.library.observer;

import com.accenture.library.email.*;

import org.springframework.stereotype.*;

@Component
public class EmailObserver implements BookObserver {

    @Override
    public void update(String isbn, String username) {
        EmailClient emailClient = EmailClient.getInstance("smtp.example.com", 25, "username", "password");
        String subject = "subject";
        String body = "Book " + isbn + " is available for you";
        emailClient.sendEmail("from@example.com", "to@example.com", subject, body);
    }
}