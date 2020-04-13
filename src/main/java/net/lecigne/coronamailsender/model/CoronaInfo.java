package net.lecigne.coronamailsender.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoronaInfo {

    @JsonProperty("country")
    private String country;

    @JsonProperty("cases")
    private long cases;

    @JsonProperty("todayCases")
    private long todayCases;

    @JsonProperty("deaths")
    private long deaths;

    @JsonProperty("todayDeaths")
    private long todayDeaths;

    @JsonProperty("recovered")
    private long recovered;

    @JsonProperty("active")
    private long active;

    @JsonProperty("critical")
    private long critical;

    @JsonProperty("casesPerOneMillion")
    private long casesPerOneMillion;

}
