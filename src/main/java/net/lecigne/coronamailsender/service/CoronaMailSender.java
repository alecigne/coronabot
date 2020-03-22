package net.lecigne.coronamailsender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class CoronaMailSender {

    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${corona.mail.recipients}")
    private String[] to;

    @Autowired
    public CoronaMailSender(JavaMailSender sender) {
        this.sender = sender;
    }

    public void sendEmail(final String subject, final String message) throws MessagingException {
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject(subject);
        helper.setText(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        sender.send(mimeMessage);
    }
}
