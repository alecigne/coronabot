package net.lecigne.coronamailsender.service;

import lombok.val;
import net.lecigne.coronamailsender.config.ReportConfig;
import net.lecigne.coronamailsender.coronainfo.CoronaInfo;
import net.lecigne.coronamailsender.coronainfo.CoronaInfoClient;
import net.lecigne.coronamailsender.coronareport.CoronaReport;
import net.lecigne.coronamailsender.coronareport.CoronaReportDao;
import net.lecigne.coronamailsender.coronareport.CoronaReportService;
import net.lecigne.coronamailsender.email.CoronaMailBuilder;
import net.lecigne.coronamailsender.email.Email;
import net.lecigne.coronamailsender.email.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static net.lecigne.coronamailsender.constants.TestConstants.COUNTRY_CASES;
import static net.lecigne.coronamailsender.constants.TestConstants.COUNTRY_DEATHS;
import static net.lecigne.coronamailsender.constants.TestConstants.WORLD_CASES;
import static net.lecigne.coronamailsender.constants.TestConstants.WORLD_DEATHS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoronaReportServiceTest {

    @Mock
    CoronaReportDao coronaReportDao;

    @Mock
    CoronaInfoClient coronaClient;

    @Mock
    EmailService emailService;

    @Mock
    CoronaMailBuilder coronaMailBuilder;

    CoronaReportService coronaReportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        val reportConfig = new ReportConfig();
        reportConfig.setCountry("france");
        coronaReportService = new CoronaReportService(coronaReportDao, coronaClient, emailService, coronaMailBuilder,
                reportConfig);
    }

    @Test
    void syncCoronaReport_whenCountryMatches_shouldStoreAndReturnInfo() {
        // Given
        String country = "france";
        String world = "world";
        CoronaInfo countryInfo = CoronaInfo.builder()
                .country(country)
                .todayCases(COUNTRY_CASES)
                .todayDeaths(COUNTRY_DEATHS)
                .build();
        CoronaInfo worldInfo = CoronaInfo.builder()
                .country(country)
                .todayCases(WORLD_CASES)
                .todayDeaths(WORLD_DEATHS)
                .build();
        when(coronaClient.getCoronaInfo(country)).thenReturn(countryInfo);
        when(coronaClient.getCoronaInfo(world)).thenReturn(worldInfo);

        CoronaReport expectedReport = CoronaReport.builder()
                .country(country)
                .countryCases(COUNTRY_CASES)
                .countryDeaths(COUNTRY_DEATHS)
                .worldCases(WORLD_CASES)
                .worldDeaths(WORLD_DEATHS)
                .build();

        // When
        Optional<CoronaReport> actualReport = coronaReportService.updateCoronaReport();

        // Then
        assertThat(actualReport).isNotEmpty().get().isEqualToComparingFieldByField(expectedReport);
        verify(coronaReportDao, times(1)).update(ArgumentMatchers.refEq(expectedReport));
    }

    @Test
    void sendCoronaReport_whenReportIsPresent_shouldSendIt() {
        // Given
        CoronaReport storedReport = CoronaReport.builder()
                .country("france")
                .countryCases(COUNTRY_CASES)
                .countryDeaths(COUNTRY_DEATHS)
                .worldCases(WORLD_CASES)
                .worldDeaths(WORLD_DEATHS)
                .build();
        when(coronaReportDao.get()).thenReturn(Optional.of(storedReport));
        when(coronaMailBuilder.buildCoronaMail(storedReport)).thenReturn(Email.builder().build());

        // When
        coronaReportService.sendCoronaReport();

        // Then
        verify(coronaMailBuilder, times(1)).buildCoronaMail(storedReport);
        verify(emailService, times(1)).send(any());
    }

    @Test
    void sendCoronaReport_whenReportIsAbsent_shouldDoNothing() {
        // Given
        when(coronaReportDao.get()).thenReturn(Optional.empty());

        // When
        coronaReportService.sendCoronaReport();

        // Then
        verify(coronaMailBuilder, never()).buildCoronaMail(any());
        verify(emailService, never()).send(any());
    }

}
