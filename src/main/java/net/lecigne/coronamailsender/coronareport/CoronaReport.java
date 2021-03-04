package net.lecigne.coronamailsender.coronareport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import net.lecigne.coronamailsender.coronainfo.CoronaInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * COVID-19 statistics about multiple entities: a country and the world.
 */
@Builder
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoronaReport {
    String country;
    long countryCases;
    long countryDeaths;
    long worldCases;
    long worldDeaths;

    @JsonIgnore
    public Map<String, Object> getModel() {
        Map<String, Object> model = new HashMap<>();
        model.put("country", this.country);
        model.put("countryCases", String.format("%,d", this.countryCases));
        model.put("countryDeaths", String.format("%,d", this.countryDeaths));
        model.put("worldCases", String.format("%,d", this.worldCases));
        model.put("worldDeaths", String.format("%,d", this.worldDeaths));
        return model;
    }

    public static CoronaReport fromCoronaInfo(CoronaInfo countryInfo, CoronaInfo worldInfo) {
        return CoronaReport.builder()
                .country(countryInfo.getCountry())
                .countryCases(countryInfo.getTodayCases())
                .countryDeaths(countryInfo.getTodayDeaths())
                .worldCases(worldInfo.getTodayCases())
                .worldDeaths(worldInfo.getTodayDeaths())
                .build();
    }
}
