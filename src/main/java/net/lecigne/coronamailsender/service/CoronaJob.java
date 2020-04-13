package net.lecigne.coronamailsender.service;

import lombok.extern.slf4j.Slf4j;
import net.lecigne.coronamailsender.client.CoronaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
                     CoronaMailBuilder coronaMailBuilder, CoronaClient coronaClient) {
        this.coronaInfoService = coronaInfoService;
        this.eMailService = eMailService;
        this.coronaMailBuilder = coronaMailBuilder;
    }

    @Scheduled(cron = "${corona.mail.cron}", zone = "Europe/Paris")
    public void executeMailJob() {
        coronaInfoService.getStoredCoronaInfo().ifPresent(coronaInfo -> {
            log.info("Sending an email");
            eMailService.sendEmail(coronaMailBuilder.buildCoronaMail(coronaInfo));
        });
    }

    @Scheduled(cron = "${corona.sync.cron}", zone = "Europe/Paris")
    private void executeSyncingJob() {
        coronaInfoService.fetchCoronaInfo(country).ifPresentOrElse(coronaInfo -> {
            log.info("Syncing and storing information about coronavirus");
            coronaInfoService.updateStoredCoronaInfo(coronaInfo);
        }, () -> {
            log.error("Error retrieving info - storing null");
            coronaInfoService.updateStoredCoronaInfo(null);
        });
    }

}
