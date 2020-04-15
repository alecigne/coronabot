package net.lecigne.coronamailsender.controller;

import lombok.extern.slf4j.Slf4j;
import net.lecigne.coronamailsender.model.CoronaInfo;
import net.lecigne.coronamailsender.service.CoronaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/corona", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class CoronaInfoController {

    private final CoronaInfoService coronaInfoService;

    @Autowired
    public CoronaInfoController(CoronaInfoService coronaInfoService) {
        this.coronaInfoService = coronaInfoService;
    }

    @GetMapping
    public ResponseEntity<CoronaInfo> getStoredCoronaInfo() {
        log.info("GET /corona");
        return coronaInfoService.getStoredCoronaInfo()
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
