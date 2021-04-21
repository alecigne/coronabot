package net.lecigne.coronamailsender.coronareport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CoronaReportTest {

    CoronaReport coronaReport;

    @BeforeEach
    void setUp() {
        coronaReport = CoronaReport.builder()
                .country("france")
                .countryCases(10L)
                .countryDeaths(100L)
                .worldCases(1_000L)
                .worldDeaths(10_000L)
                .build();
    }

    @Test
    void getModel() {
        // Given
        Map<String, Object> expectedModel = Map.of(
                "country", "france",
                "countryCases", "10",
                "countryDeaths", "100",
                "worldCases", "1 000".replaceAll(" ", "\u00A0"),
                "worldDeaths", "10 000".replaceAll(" ", "\u00A0"));

        // When
        Map<String, Object> model = coronaReport.getModel();

        // Then
        assertThat(model).containsExactlyInAnyOrderEntriesOf(expectedModel);
    }
}
