package com.master.minieshop.service;

import com.master.minieshop.model.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}") private String sender;
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendHtmlEmail(EmailDetails emailDetails) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(sender);
        message.setRecipients(MimeMessage.RecipientType.TO, emailDetails.getRecipient());
        message.setSubject(emailDetails.getSubject());

        message.setContent(emailDetails.getContent(), "text/html; charset=utf-8");

        mailSender.send(message);
    }
}