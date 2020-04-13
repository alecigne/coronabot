package net.lecigne.coronamailsender.controller;

import net.lecigne.coronamailsender.model.CoronaInfo;
import net.lecigne.coronamailsender.service.CoronaInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static net.lecigne.coronamailsender.controller.ResponseBodyMatchers.responseBody;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CoronaInfoController.class)
public class CoronaInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoronaInfoService coronaInfoService;

    @Test
    public void getCoronaInfo_shouldWork() throws Exception {
        // Given
        CoronaInfo coronaInfo = CoronaInfo.builder()
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

        when(coronaInfoService.getStoredCoronaInfo()).thenReturn(Optional.of(coronaInfo));

        // When - Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/corona/france"))
                .andExpect(status().isOk())
                .andExpect(responseBody().containsObjectAsJson(coronaInfo, CoronaInfo.class));
    }

}