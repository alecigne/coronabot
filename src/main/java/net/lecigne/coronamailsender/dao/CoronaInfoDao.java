package net.lecigne.coronamailsender.dao;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.lecigne.coronamailsender.client.CoronaClient;
import net.lecigne.coronamailsender.exception.DataAccessException;
import net.lecigne.coronamailsender.model.CoronaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class CoronaInfoDao {

    private CoronaInfo storedCoronaInfo;

    final private CoronaClient coronaClient;

    @Autowired
    public CoronaInfoDao(CoronaClient coronaClient) {
        this.coronaClient = coronaClient;
    }

    public CoronaInfo getStoredCoronaInfo() {
        if (storedCoronaInfo != null) {
            return storedCoronaInfo;
        } else {
            throw new DataAccessException("Stored data is null");
        }
    }

    public void updateStoredCoronaInfo(CoronaInfo coronaInfo) {
        this.storedCoronaInfo = coronaInfo;
    }

    public CoronaInfo fetchCoronaInfo(String country) {
        try {
            return coronaClient.getCoronaInfo(country);
        } catch (Exception e) {
            throw new DataAccessException("Error fetching data", e);
        }
    }

}
