package net.lecigne.coronamailsender.email;

import net.lecigne.coronamailsender.config.MailConfig;
import net.lecigne.coronamailsender.coronareport.CoronaReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Build an email from a COVID-19 statistics report.
 */
@Component
public class CoronaMailBuilder {

    private final SpringTemplateEngine springTemplateEngine;
    private final MailConfig mailConfig;

    @Autowired
    public CoronaMailBuilder(SpringTemplateEngine springTemplateEngine, MailConfig mailConfig) {
        this.springTemplateEngine = springTemplateEngine;
        this.mailConfig = mailConfig;
    }

    public Email buildCoronaMail(CoronaReport coronaReport) {
        Context context = new Context();
        context.setVariables(coronaReport.getModel());
        String text = springTemplateEngine.process("mail", context);
        return new Email(mailConfig.getFrom(), mailConfig.getTo().toArray(new String[0]),
                mailConfig.getSubject(), text);
    }

}
