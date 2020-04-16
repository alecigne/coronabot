package net.lecigne.coronamailsender.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CoronaJob {

    final CoronaInfoService coronaInfoService;

    final EMailService eMailService;

    final CoronaMailBuilder coronaMailBuilder;

    @Value("${corona.mail.country}")
    private String country;

    @Autowired
    public CoronaJob(CoronaInfoService coronaInfoService, EMailService eMailService,
                     CoronaMailBuilder coronaMailBuilder) {
        this.coronaInfoService = coronaInfoService;
        this.eMailService = eMailService;
        this.coronaMailBuilder = coronaMailBuilder;
    }

    @Scheduled(cron = "${corona.mail.cron}", zone = "Europe/Paris")
    public void executeMailJob() {
        coronaInfoService.getCoronaInfo().ifPresent(coronaInfo -> {
            log.info("Sending an email");
            eMailService.sendEmail(coronaMailBuilder.buildCoronaMail(coronaInfo));
        });
    }

    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "${corona.sync.cron}", zone = "Europe/Paris")
    public void executeSyncingJob() {
        coronaInfoService.syncCoronaInfo(country);
    }

}
