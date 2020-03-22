package net.lecigne.coronamailsender.wsc;

import net.lecigne.coronamailsender.model.CoronaInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "coronaClient", url = "https://coronavirus-19-api.herokuapp.com")
public interface CoronaClient {

    @RequestMapping(method = RequestMethod.GET, value = "/countries/{country}")
    CoronaInfo getCoronaInfo(@PathVariable("country") String country);

}
