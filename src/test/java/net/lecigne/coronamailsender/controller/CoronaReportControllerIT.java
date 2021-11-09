package net.lecigne.coronamailsender.controller;

import net.lecigne.coronamailsender.coronareport.CoronaReport;
import net.lecigne.coronamailsender.coronareport.CoronaReportController;
import net.lecigne.coronamailsender.coronareport.CoronaReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static net.lecigne.coronamailsender.constants.TestConstants.COUNTRY_CASES;
import static net.lecigne.coronamailsender.constants.TestConstants.COUNTRY_DEATHS;
import static net.lecigne.coronamailsender.constants.TestConstants.WORLD_CASES;
import static net.lecigne.coronamailsender.constants.TestConstants.WORLD_DEATHS;
import static net.lecigne.coronamailsender.controller.ResponseBodyMatchers.responseBody;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CoronaReportController.class)
class CoronaReportControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoronaReportService coronaReportService;

    @Test
    void getCoronaReport_whenValidInfoIsStored_shouldReturnInfo() throws Exception {
        // Given
        var report = CoronaReport.builder()
                .country("france")
                .countryCases(COUNTRY_CASES)
                .countryDeaths(COUNTRY_DEATHS)
                .worldCases(WORLD_CASES)
                .worldDeaths(WORLD_DEATHS)
                .build();
        when(coronaReportService.getCoronaReport()).thenReturn(Optional.of(report));

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/corona"))
                // Then
                .andExpect(status().isOk())
                .andExpect(responseBody().containsObjectAsJson(report, CoronaReport.class));
    }

    @Test
    void getCoronaReport_whenNothingIsStored_shouldThrowHttp500() throws Exception {
        // Given
        when(coronaReportService.getCoronaReport()).thenReturn(Optional.empty());

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/corona"))
                // Then
                .andExpect(status().is5xxServerError());
    }

    @Test
    void syncCoronaInfo_whenSyncingIsOk_shouldReturnInfo() throws Exception {
        // Given
        final String country = "france";

        var report = CoronaReport.builder()
                .country("france")
                .countryCases(COUNTRY_CASES)
                .countryDeaths(COUNTRY_DEATHS)
                .worldCases(WORLD_CASES)
                .worldDeaths(WORLD_DEATHS)
                .build();

        when(coronaReportService.updateCoronaReport()).thenReturn(Optional.of(report));

        // When
        mockMvc.perform(MockMvcRequestBuilders.put("/corona"))
                // Then
                .andExpect(status().isOk())
                .andExpect(responseBody().containsObjectAsJson(report, CoronaReport.class));
        verify(coronaReportService, times(1)).updateCoronaReport();
    }

    @Test
    void syncCoronaInfo_whenSyncingFails_shouldReturnHttp500() throws Exception {
        // Given
        final String country = "france";
        when(coronaReportService.updateCoronaReport()).thenReturn(Optional.empty());

        // When
        mockMvc.perform(MockMvcRequestBuilders.put("/corona"))
                // Then
                .andExpect(status().is5xxServerError());
        verify(coronaReportService, times(1)).updateCoronaReport();
    }

}
