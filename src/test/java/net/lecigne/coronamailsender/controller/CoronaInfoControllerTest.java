package net.lecigne.coronamailsender.controller;

import net.lecigne.coronamailsender.model.CoronaInfo;
import net.lecigne.coronamailsender.service.CoronaInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static net.lecigne.coronamailsender.controller.ResponseBodyMatchers.responseBody;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CoronaInfoController.class)
public class CoronaInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoronaInfoService coronaInfoService;

    @Test
    public void getCoronaInfo_whenValidInfoIsStored_shouldReturnInfo() throws Exception {
        // Given
        CoronaInfo storedCoronaInfo = CoronaInfo.builder()
                .country("france")
                .cases(132591)
                .todayCases(2937)
                .deaths(14393)
                .todayDeaths(561)
                .recovered(27186)
                .active(91012)
                .critical(6845)
                .casesPerOneMillion(2031)
                .build();

        when(coronaInfoService.getCoronaInfo()).thenReturn(Optional.of(storedCoronaInfo));

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/corona"))
                // Then
                .andExpect(status().isOk())
                .andExpect(responseBody().containsObjectAsJson(storedCoronaInfo, CoronaInfo.class));
    }

    @Test
    public void getCoronaInfo_whenNothingIsStored_shouldThrowHttp500() throws Exception {
        // Given
        when(coronaInfoService.getCoronaInfo()).thenReturn(Optional.empty());

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/corona"))
                // Then
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void syncCoronaInfo_whenSyncingIsOk_shouldReturnInfo() throws Exception {
        // Given
        final String country = "france";

        CoronaInfo syncedCoronaInfo = CoronaInfo.builder()
                .country(country)
                .cases(132591)
                .todayCases(2937)
                .deaths(14393)
                .todayDeaths(561)
                .recovered(27186)
                .active(91012)
                .critical(6845)
                .casesPerOneMillion(2031)
                .build();

        when(coronaInfoService.syncCoronaInfo(country)).thenReturn(Optional.of(syncedCoronaInfo));

        // When
        mockMvc.perform(MockMvcRequestBuilders.put("/corona/" + country))
                // Then
                .andExpect(status().isOk())
                .andExpect(responseBody().containsObjectAsJson(syncedCoronaInfo, CoronaInfo.class));
        verify(coronaInfoService, times(1)).syncCoronaInfo(country);
    }

    @Test
    public void syncCoronaInfo_whenSyncingFails_shouldReturnHttp500() throws Exception {
        // Given
        final String country = "france";
        when(coronaInfoService.syncCoronaInfo(country)).thenReturn(Optional.empty());

        // When
        mockMvc.perform(MockMvcRequestBuilders.put("/corona/" + country))
                // Then
                .andExpect(status().is5xxServerError());
        verify(coronaInfoService, times(1)).syncCoronaInfo(country);
    }

}
