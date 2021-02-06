package net.lecigne.coronamailsender.coronainfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

/**
 * COVID-19 statistics for a specific entity (a country or the world).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Getter
public class CoronaInfo {
    private final String country;
    private final long cases;
    private final long todayCases;
    private final long deaths;
    private final long todayDeaths;
    private final long recovered;
    private final long active;
    private final long critical;
    private final long casesPerOneMillion;
}
