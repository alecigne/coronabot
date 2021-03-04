package net.lecigne.coronamailsender.coronainfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * COVID-19 statistics for a specific entity (a country or the world).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoronaInfo {
    String country;
    long cases;
    long todayCases;
    long deaths;
    long todayDeaths;
    long recovered;
    long active;
    long critical;
    long casesPerOneMillion;
}
