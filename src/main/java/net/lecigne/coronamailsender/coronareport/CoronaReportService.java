package net.lecigne.coronamailsender.coronareport;

import lombok.extern.slf4j.Slf4j;
import net.lecigne.coronamailsender.coronainfo.CoronaInfo;
import net.lecigne.coronamailsender.coronainfo.CoronaInfoClient;
import net.lecigne.coronamailsender.email.CoronaMailBuilder;
import net.lecigne.coronamailsender.email.Email;
import net.lecigne.coronamailsender.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Manage COVID-19 reports.
 */
@Service
@Slf4j
public class CoronaReportService {

    private final CoronaReportDao coronaReportDao;
    private final CoronaInfoClient coronaInfoClient;
    private final EmailService eMailService;
    private final CoronaMailBuilder coronaMailBuilder;
    private final String country;

    @Autowired
    public CoronaReportService(CoronaReportDao coronaReportDao, CoronaInfoClient coronaClient,
                               EmailService emailService, CoronaMailBuilder coronaMailBuilder,
                               @Value("${corona.mail.country}") String country) {
        this.coronaReportDao = coronaReportDao;
        this.coronaInfoClient = coronaClient;
        this.eMailService = emailService;
        this.coronaMailBuilder = coronaMailBuilder;
        this.country = country;
    }

    public Optional<CoronaReport> getCoronaReport() {
        return coronaReportDao.get();
    }

    public void updateCoronaReport(CoronaReport coronaReport) {
        coronaReportDao.update(coronaReport);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "${corona.sync.cron}", zone = "Europe/Paris")
    public Optional<CoronaReport> updateCoronaReport() {
        log.info("Updating the COVID-19 report with today's data");
        try {
            CoronaInfo countryInfo = coronaInfoClient.getCoronaInfo(country.toLowerCase());
            CoronaInfo worldInfo = coronaInfoClient.getCoronaInfo("world");
            CoronaReport report = CoronaReport.builder()
                    .country(country)
                    .countryCases(countryInfo.getTodayCases())
                    .countryDeaths(countryInfo.getTodayDeaths())
                    .worldCases(worldInfo.getTodayCases())
                    .worldDeaths(worldInfo.getTodayDeaths())
                    .build();
            updateCoronaReport(report);
            return Optional.of(report);
        } catch (Exception e) {
            log.warn("Update failed - storing an empty report");
            updateCoronaReport(null);
        }
        return Optional.empty();
    }

    @Scheduled(cron = "${corona.mail.cron}", zone = "Europe/Paris")
    public void sendCoronaReport() {
        getCoronaReport().ifPresentOrElse(report -> {
            log.info("Sending an email");
            Email email = coronaMailBuilder.buildCoronaMail(report);
            eMailService.send(email);
        }, () -> log.warn("Email not sent - empty report"));
    }

}
