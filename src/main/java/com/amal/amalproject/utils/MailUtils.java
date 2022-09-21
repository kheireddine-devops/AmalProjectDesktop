package com.amal.amalproject.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class MailUtils {

    private static final String USERNAME = "mkd.dev.ops@gmail.com";
    private static final String PASSWORD = "coiebhbrufhqgmoh";
    private static Session session = null;

    private MailUtils() {}

    private static Session getSession() {

        if (session == null) {

            Properties propGmailSSL = new Properties();
            propGmailSSL.put("mail.smtp.host", "smtp.gmail.com");
            propGmailSSL.put("mail.smtp.port", "465");
            propGmailSSL.put("mail.smtp.auth", "true");
            propGmailSSL.put("mail.smtp.socketFactory.port", "465");
            propGmailSSL.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            session = Session.getInstance(propGmailSSL, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USERNAME, PASSWORD);
                }
            });
        }

        return session;
    }

    public static boolean sendHtmlMail(String to, String subject, String content)  {
        try{
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(USERNAME
            ));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(content, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (AddressException e) {
           System.out.println(e.getMessage());
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean sendTextMail(String to, String subject, String message) {

        return false;
    }

    public static boolean sendMailFile(String to, String subject, String content,String filename)  {
        try{
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress("amal@contact.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(content, "text/html; charset=utf-8");

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(new File(filename));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (AddressException e) {
            System.out.println(e.getMessage());
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
