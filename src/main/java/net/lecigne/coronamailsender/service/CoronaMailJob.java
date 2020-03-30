package net.lecigne.coronamailsender.service;

import lombok.extern.slf4j.Slf4j;
import net.lecigne.coronamailsender.model.CoronaInfo;
import net.lecigne.coronamailsender.wsc.CoronaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@Slf4j
public class CoronaMailJob {

    final CoronaClient coronaClient;

    final CoronaMailSender coronaMailSender;

    @Autowired
    public CoronaMailJob(CoronaClient coronaClient, CoronaMailSender coronaMailSender) {
        this.coronaClient = coronaClient;
        this.coronaMailSender = coronaMailSender;
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

        if (coronaInfo != null && coronaInfo.getCases() != 0) {
            log.info("Sending an email");
            try {
                coronaMailSender.sendEmail("Informations COVID-19", String.format("Nombre de cas : %d", coronaInfo.getCases()));
                log.info("Email sent successfully");
            } catch (MessagingException e) {
                log.error("Error sending email");
            }
        }
    }

}
