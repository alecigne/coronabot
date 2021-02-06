package net.lecigne.coronamailsender.coronareport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Provide a REST API for managing COVID-19 reports: get or update the stored report.
 */
@RestController
@RequestMapping(path = "/corona", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class CoronaReportController {

    private final CoronaReportService coronaReportService;

    @Autowired
    public CoronaReportController(CoronaReportService coronaReportService) {
        this.coronaReportService = coronaReportService;
    }

    /**
     * Retrieve the statistics report stored in memory.
     *
     * @return The report wrapped in a ResponseEntity.
     */
    @GetMapping
    public ResponseEntity<CoronaReport> getCoronaReport() {
        log.info("Retrieving the stored report");
        return coronaReportService.getCoronaReport()
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(INTERNAL_SERVER_ERROR));
    }

    /**
     * Force an update of the statistics report.
     *
     * @return The new report wrapped in a ResponseEntity.
     */
    @PutMapping
    public ResponseEntity<CoronaReport> updateCoronaReport() {
        log.info("Forcing the update of the stored report");
        return coronaReportService.updateCoronaReport()
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(INTERNAL_SERVER_ERROR));
    }

}
