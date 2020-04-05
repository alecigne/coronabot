package net.lecigne.coronamailsender.service;

import net.lecigne.coronamailsender.model.CoronaInfo;
import net.lecigne.coronamailsender.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

@Service
public class CoronaMailBuilder {

    @Value("${spring.mail.username}")
    String from;

    @Value("${corona.mail.recipients}")
    String[] to;

    @Value("${corona.mail.subject}")
    String subject;

    final SpringTemplateEngine springTemplateEngine;

    @Autowired
    public CoronaMailBuilder(SpringTemplateEngine springTemplateEngine) {
        this.springTemplateEngine = springTemplateEngine;
    }

    public Mail buildCoronaMail(CoronaInfo coronaInfo) {
        Context context = new Context();
        Map<String, Object> model = new HashMap<>();
        model.put("country", coronaInfo.getCountry());
        model.put("todayCases", coronaInfo.getTodayCases());
        model.put("todayDeaths", coronaInfo.getTodayDeaths());
        model.put("active", coronaInfo.getActive());
        model.put("critical", coronaInfo.getCritical());
        model.put("cases", coronaInfo.getCases());
        model.put("deaths", coronaInfo.getDeaths());
        model.put("recovered", coronaInfo.getRecovered());
        context.setVariables(model);
        String text = springTemplateEngine.process("mail_body", context);
        return new Mail(from, to, subject, text);
    }
}
