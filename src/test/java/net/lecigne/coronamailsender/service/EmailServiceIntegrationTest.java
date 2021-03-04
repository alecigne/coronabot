package net.lecigne.coronamailsender.service;

import com.icegreen.greenmail.store.FolderException;
import com.icegreen.greenmail.store.MailFolder;
import com.icegreen.greenmail.store.StoredMessage;
import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import net.lecigne.coronamailsender.email.Email;
import net.lecigne.coronamailsender.email.EmailService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@RunWith(SpringRunner.class)
class EmailServiceIntegrationTest {

    @Autowired
    EmailService eMailService;
    private GreenMail greenMail;

    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(ServerSetupTest.SMTP);
        greenMail.start();
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
    }

    @Test
    void sendEmail() throws MessagingException, FolderException {
        // Given
        final String from = "no-reply@coronabot.net";
        final String recipient1Email = "recipient1@gmail.com";
        final String recipient2Email = "recipient2@gmail.com";
        final String subject = "Informations COVID-19";
        final String content = "content";

        Email email = Email.builder().from(from).to(new String[]{recipient1Email, recipient2Email})
                .subject(subject).text(content).build();

        GreenMailUser recipient1 = greenMail.setUser("recipient1@gmail.com", null);
        GreenMailUser recipient2 = greenMail.setUser("recipient2@gmail.com", null);

        // When
        eMailService.send(email);

        // Then
        assertEquals(2, greenMail.getReceivedMessages().length);

        MailFolder inbox1 = greenMail.getManagers().getImapHostManager().getInbox(recipient1);
        List<StoredMessage> messages1 = inbox1.getMessages();
        assertEquals(1, messages1.size());

        MimeMessage mimeMessage1 = messages1.get(0).getMimeMessage();
        assertEquals(from, mimeMessage1.getFrom()[0].toString());
        assertNull(mimeMessage1.getAllRecipients()); // Bcc
        assertEquals(subject, mimeMessage1.getSubject());

        MailFolder inbox2 = greenMail.getManagers().getImapHostManager().getInbox(recipient2);
        List<StoredMessage> messages2 = inbox2.getMessages();
        assertEquals(1, messages2.size());

        MimeMessage mimeMessage2 = messages2.get(0).getMimeMessage();
        assertEquals(from, mimeMessage2.getFrom()[0].toString());
        assertNull(mimeMessage2.getAllRecipients()); // Bcc
        assertEquals(subject, mimeMessage2.getSubject());
    }

}
