package net.lecigne.coronamailsender.coronareport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Store the current report in memory.
 */
@Slf4j
@Repository
public class CoronaReportDao {

    private CoronaReport coronaReport;

    public Optional<CoronaReport> get() {
        return Optional.ofNullable(coronaReport);
    }

    public void update(CoronaReport coronaReport) {
        this.coronaReport = coronaReport;
    }
}
