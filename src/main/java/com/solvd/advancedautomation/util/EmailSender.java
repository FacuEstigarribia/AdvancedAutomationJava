package com.solvd.advancedautomation.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.search.SubjectTerm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EmailSender {

    private static final String CONFIG_FILE = "data.properties";
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream(CONFIG_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static final Log LOGGER = LogFactory.getLog(EmailSender.class);
    private static final String EMAIL_FROM = properties.getProperty("email_from");
    private static final String EMAIL_TO = properties.getProperty("email_to");
    private static final String APP_PASSWORD = properties.getProperty("app_password");

    private static final String EMAIL_SUBJECT = "Email subject";
    private static final String EMAIL_CONTENT = "This is my email sent from Gmail using Java";

    public static void main(String[] args) throws Exception {
        // Send email
        sendEmail();

        // Verify email reception
        boolean isEmailReceived = verifyEmailReception();
        if (isEmailReceived) {
            LOGGER.info("Email received successfully.");
        } else {
            LOGGER.info("Email not received.");
        }
    }
    private static void sendEmail() throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(EMAIL_FROM));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO));
        message.setSubject(EMAIL_SUBJECT);
        message.setText(EMAIL_CONTENT);
        Transport.send(message);
        LOGGER.info("Email sent successfully.");
    }

    private static boolean verifyEmailReception() throws Exception {
        // Wait a few seconds to allow email to be delivered
        Thread.sleep(5000);

        // Get email session for receiving emails
        Session session = getEmailSession();
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", EMAIL_FROM, APP_PASSWORD);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        // Search for the email with the specific subject
        Message[] messages = inbox.search(new SubjectTerm(EMAIL_SUBJECT));
        for (Message message : messages) {
            if (message.getSubject().equals(EMAIL_SUBJECT) && message.getContent().toString().contains(EMAIL_CONTENT)) {
                inbox.close(false);
                store.close();
                return true;
            }
        }
        inbox.close(false);
        store.close();
        return false;
    }

    private static Session getEmailSession() {
        return Session.getInstance(getGmailProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, APP_PASSWORD);
            }
        });
    }

    private static Properties getGmailProperties() {
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
