package com.solvd.advancedautomation.util;

import jakarta.mail.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class EmailSender {
    protected static final Properties properties = DataLoader.getProperties();
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);
    private static final String EMAIL_FROM = properties.getProperty("email_from");
    private static final String APP_PASSWORD = properties.getProperty("app_password");

    public  String readEmail(String email, String password) throws Exception {
        // Wait a few seconds to allow email to be delivered
        Thread.sleep(5000);

        //var to save email id
        String idEmail = "";

        // Get email session for receiving emails
        Session session = getEmailSession();
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", email, password);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        Message[] messages = inbox.getMessages();
        for (Message message : messages) {
            idEmail = message.getSubject().replace("SendTestEmail.com - Testing Email ID: ", "");

        }
        LOGGER.info("ID Email: " + idEmail);
        inbox.close(false);
        store.close();
        return idEmail;
    }

    private  Session getEmailSession() {
        return Session.getInstance(getGmailProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, APP_PASSWORD);
            }
        });
    }

    private  Properties getGmailProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.imap.ssl.enable", "true");
        return prop;
    }
}
