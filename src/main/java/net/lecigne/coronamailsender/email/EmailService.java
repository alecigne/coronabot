package net.lecigne.coronamailsender.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Send emails.
 */
@Service
@Slf4j
public class EmailService {

    private final JavaMailSender sender;

    @Autowired
    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void send(final Email email) {
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setSubject(email.getSubject());
            helper.setText(email.getText(), false);
            helper.setFrom(email.getFrom());
            helper.setBcc(email.getTo());
            sender.send(mimeMessage);
            log.info("Email sent successfully");
        } catch (MessagingException | MailException e) {
            log.error("Error sending Email");
        }
    }

}
