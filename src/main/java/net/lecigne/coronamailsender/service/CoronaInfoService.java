package net.lecigne.coronamailsender.service;

import lombok.extern.slf4j.Slf4j;
import net.lecigne.coronamailsender.dao.CoronaInfoDao;
import net.lecigne.coronamailsender.exception.DataAccessException;
import net.lecigne.coronamailsender.model.CoronaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CoronaInfoService {

    final CoronaInfoDao coronaInfoDao;

    @Autowired
    public CoronaInfoService(CoronaInfoDao coronaInfoDao) {
        this.coronaInfoDao = coronaInfoDao;
    }

    public Optional<CoronaInfo> getStoredCoronaInfo() {
        try {
            return Optional.of(coronaInfoDao.getStoredCoronaInfo());
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public void updateStoredCoronaInfo(CoronaInfo coronaInfo) {
        coronaInfoDao.updateStoredCoronaInfo(coronaInfo);
    }

    public Optional<CoronaInfo> fetchCoronaInfo(String country) {
        try {
            return Optional.of(coronaInfoDao.fetchCoronaInfo(country));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

}
