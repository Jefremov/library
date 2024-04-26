package com.accenture.libraryManaging.email;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class EmailClientTest {


    @Test
    void getInstanceReturnsSameInstance() {
        EmailClient instance1 = EmailClient.getInstance("smtpHost", 25, "username", "password");
        EmailClient instance2 = EmailClient.getInstance("smtpHost", 25, "username", "password");
        assertSame(instance1, instance2);
    }

    @Test
    void getInstanceReturnsSameInstanceEvenWithDifferentParameters() {
        EmailClient instance1 = EmailClient.getInstance("smtpHost", 25, "username", "password");
        EmailClient instance2 = EmailClient.getInstance("differentSmtpHost", 30, "differentUsername", "differentPassword");
        assertSame(instance1, instance2);
    }
}