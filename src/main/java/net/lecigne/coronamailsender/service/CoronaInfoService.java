package net.lecigne.coronamailsender.service;

import lombok.extern.slf4j.Slf4j;
import net.lecigne.coronamailsender.client.CoronaClient;
import net.lecigne.coronamailsender.dao.CoronaInfoDao;
import net.lecigne.coronamailsender.exception.DataAccessException;
import net.lecigne.coronamailsender.exception.ServiceException;
import net.lecigne.coronamailsender.model.CoronaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CoronaInfoService {

    private final CoronaInfoDao coronaInfoDao;

    private final CoronaClient coronaClient;

    @Autowired
    public CoronaInfoService(CoronaInfoDao coronaInfoDao, CoronaClient coronaClient) {
        this.coronaInfoDao = coronaInfoDao;
        this.coronaClient = coronaClient;
    }

    public Optional<CoronaInfo> getCoronaInfo() {
        try {
            return Optional.of(coronaInfoDao.getCoronaInfo());
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public void updateCoronaInfo(CoronaInfo coronaInfo) {
        coronaInfoDao.updateCoronaInfo(coronaInfo);
    }

    public Optional<CoronaInfo> syncCoronaInfo(String country) {
        try {
            CoronaInfo coronaInfo = fetchCoronaInfo(country);
            updateCoronaInfo(coronaInfo);
            return Optional.of(coronaInfo);
        } catch (Exception e) {
            updateCoronaInfo(null);
            return Optional.empty();
        }
    }

    private CoronaInfo fetchCoronaInfo(String country) {
        CoronaInfo coronaInfo = coronaClient.getCoronaInfo(country);
        if (!country.equalsIgnoreCase(coronaInfo.getCountry())) {
            throw new ServiceException("Country doesn't match");
        }
        return coronaInfo;
    }

}
