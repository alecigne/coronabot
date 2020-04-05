package net.lecigne.coronamailsender.service;

import lombok.extern.slf4j.Slf4j;
import net.lecigne.coronamailsender.model.CoronaInfo;
import net.lecigne.coronamailsender.client.CoronaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CoronaInfoService {

    final CoronaClient coronaClient;

    @Autowired
    public CoronaInfoService(CoronaClient coronaClient) {
        this.coronaClient = coronaClient;
    }

    public Optional<CoronaInfo> getCoronaInfo(String country) {
        try {
            log.info("Retrieving information about coronavirus");
            return Optional.of(coronaClient.getCoronaInfo(country));
        } catch (Exception e) {
            log.error("Error retrieving info");
        }
        return Optional.empty();
    }

}
