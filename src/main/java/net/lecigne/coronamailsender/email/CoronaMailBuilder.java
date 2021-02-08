package net.lecigne.coronamailsender.email;

import net.lecigne.coronamailsender.coronareport.CoronaReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Build an email from a COVID-19 statistics report.
 */
@Component
public class CoronaMailBuilder {

    private final SpringTemplateEngine springTemplateEngine;
    private final String from;
    private final String[] to;
    private final String subject;

    @Autowired
    public CoronaMailBuilder(SpringTemplateEngine springTemplateEngine, @Value("${spring.mail.username}") String from,
                             @Value("${corona.mail.recipients}") String[] to, @Value("${corona.mail.subject}") String subject) {
        this.springTemplateEngine = springTemplateEngine;
        this.from = from;
        this.to = to;
        this.subject = subject;
    }

    public Email buildCoronaMail(CoronaReport coronaReport) {
        Context context = new Context();
        context.setVariables(coronaReport.getModel());
        String text = springTemplateEngine.process("mail", context);
        return new Email(from, to, subject, text);
    }

}
