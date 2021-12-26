package net.lecigne.coronamailsender.service;

import net.lecigne.coronamailsender.email.Email;
import net.lecigne.coronamailsender.email.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @InjectMocks
    EmailService eMailService;

    @Mock
    JavaMailSender javaMailSenderMock;

    @Captor
    ArgumentCaptor<MimeMessage> mimeMessageCaptor;

    @Test
    void sendMail_shouldSendCorrectEmail() throws MessagingException, IOException {

        // Given
        final String from = "no-reply@coronabot.net";
        final String[] to = new String[]{"recipient1@gmail.com", "recipient2@gmail.com"};
        final String subject = "Informations COVID-19";
        final String content = "content";

        Email email = Email.builder()
                .from(from)
                .to(to)
                .subject(subject)
                .text(content)
                .build();

        Session session = null;
        MimeMessage mimeMessage = new MimeMessage(session);
        when(javaMailSenderMock.createMimeMessage()).thenReturn(mimeMessage);
        doNothing().when(javaMailSenderMock).send(mimeMessageCaptor.capture());

        // When
        eMailService.send(email);
        MimeMessage result = mimeMessageCaptor.getValue();

        // Then
        assertEquals(from, result.getFrom()[0].toString());
        assertEquals(2, result.getRecipients(Message.RecipientType.BCC).length);
        assertEquals(to[0], result.getRecipients(Message.RecipientType.BCC)[0].toString());
        assertEquals(to[1], result.getRecipients(Message.RecipientType.BCC)[1].toString());
        assertEquals(subject, result.getSubject());
        assertEquals(content, result.getContent());
    }

}
