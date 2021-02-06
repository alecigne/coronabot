package net.lecigne.coronamailsender.coronainfo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * A REST client for COVID-19 statistics.
 */
@FeignClient(value = "coronaClient", url = "https://coronavirus-19-api.herokuapp.com")
public interface CoronaInfoClient {

    @GetMapping(value = "/countries/{country}")
    CoronaInfo getCoronaInfo(@PathVariable("country") String country);

}
