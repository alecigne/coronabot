package net.lecigne.coronamailsender.service;

import net.lecigne.coronamailsender.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class CoronaMailSender {

    private JavaMailSender sender;

    @Autowired
    public CoronaMailSender(JavaMailSender sender) {
        this.sender = sender;
    }

    public void sendEmail(final Mail mail) throws MessagingException {
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getText(), false);
        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getTo());
        sender.send(mimeMessage);
    }
}
