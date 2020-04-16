package net.lecigne.coronamailsender.dao;

import lombok.extern.slf4j.Slf4j;
import net.lecigne.coronamailsender.client.CoronaClient;
import net.lecigne.coronamailsender.exception.DataAccessException;
import net.lecigne.coronamailsender.model.CoronaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class CoronaInfoDao {

    private CoronaInfo coronaInfo;

    public CoronaInfo getCoronaInfo() {
        if (coronaInfo != null) {
            return coronaInfo;
        } else {
            throw new DataAccessException("Stored data is null");
        }
    }

    public void updateCoronaInfo(CoronaInfo coronaInfo) {
        this.coronaInfo = coronaInfo;
    }

}
