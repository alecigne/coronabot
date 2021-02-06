package net.lecigne.coronamailsender.coronareport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * COVID-19 statistics about multiple entities: a country and the world.
 */
@Builder
@Getter
public class CoronaReport {
    private final String country;
    private final long countryCases;
    private final long countryDeaths;
    private final long worldCases;
    private final long worldDeaths;

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
}
