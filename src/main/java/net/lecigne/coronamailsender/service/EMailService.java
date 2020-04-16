package net.lecigne.coronamailsender.service;

import lombok.extern.slf4j.Slf4j;
import net.lecigne.coronamailsender.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EMailService {

    private JavaMailSender sender;

    @Autowired
    public EMailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void sendEmail(final Mail mail) {
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getText(), false);
            helper.setFrom(mail.getFrom());
            helper.setBcc(mail.getTo());
            sender.send(mimeMessage);
        } catch (MessagingException | MailException e) {
            log.error("Error sending Email");
        }
        log.info("Email sent successfully");
    }
}
