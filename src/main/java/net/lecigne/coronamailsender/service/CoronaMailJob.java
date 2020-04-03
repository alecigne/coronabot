package net.lecigne.coronamailsender.service;

import lombok.extern.slf4j.Slf4j;
import net.lecigne.coronamailsender.model.CoronaInfo;
import net.lecigne.coronamailsender.model.Mail;
import net.lecigne.coronamailsender.wsc.CoronaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CoronaMailJob {

    @Value("${spring.mail.username}")
    String from;

    @Value("${corona.mail.recipients}")
    String[] to;

    @Value("${corona.mail.subject}")
    String subject;

    final CoronaClient coronaClient;

    final CoronaMailSender coronaMailSender;

    final SpringTemplateEngine springTemplateEngine;

    @Autowired
    public CoronaMailJob(CoronaClient coronaClient, CoronaMailSender coronaMailSender,
                         SpringTemplateEngine springTemplateEngine) {
        this.coronaClient = coronaClient;
        this.coronaMailSender = coronaMailSender;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Scheduled(cron = "${corona.mail.cron}", zone = "Europe/Paris")
    public void execute() {
        CoronaInfo coronaInfo = null;

        try {
            log.info("Retrieving information about coronavirus");
            coronaInfo = coronaClient.getCoronaInfo("france");
        } catch (Exception e) {
            log.error("Error retrieving info");
        }

        if (coronaInfo != null) {
            log.info("Sending an email");

            Context context = new Context();
            Map<String, Object> model = new HashMap();
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

            Mail coronaMail = new Mail(from, to, subject, text);

            try {
                coronaMailSender.sendEmail(coronaMail);
                log.info("Email sent successfully");
            } catch (MessagingException e) {
                log.error("Error sending email");
            }
        }
    }

}
