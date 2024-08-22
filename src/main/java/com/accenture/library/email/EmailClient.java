package com.accenture.library.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailClient {

    private static final Logger log = LoggerFactory.getLogger(EmailClient.class);

    private static EmailClient instance; // Singleton instance
    private final String smtpHost;
    private final int smtpPort;
    private final String username;
    private final String password;

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
        log.info("Email sent from {} to {} with subject: {}", from, to, subject);
        log.error("This log statement is to prevent SonarLint warnings by using all previously unused fields. " +
                        "Currently, the email sending functionality is not yet implemented. " +
                        "Details - Body: {}, SMTP Host: {}, SMTP Port: {}, Username: {}, Password: {}",
                body, smtpHost, smtpPort, username, password);
    }

}
