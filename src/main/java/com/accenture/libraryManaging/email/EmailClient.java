package com.accenture.libraryManaging.email;


public class EmailClient {

    private static EmailClient instance; // Singleton instance
    private String smtpHost;
    private int smtpPort;
    private String username;
    private String password;

    private EmailClient(String smtpHost, int smtpPort, String username, String password) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.username = username;
        this.password = password;
        // Initialize email connection here
    }

    public static EmailClient getInstance(String smtpHost, int smtpPort, String username, String password) {
        if (instance == null) {
            instance = new EmailClient(smtpHost, smtpPort, username, password);
        }
        return instance;
    }

    public void sendEmail(String from, String to, String subject, String body) {
        // Implementation for sending email
    }

}
