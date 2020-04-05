package net.lecigne.coronamailsender.controller;

import lombok.extern.slf4j.Slf4j;
import net.lecigne.coronamailsender.model.CoronaInfo;
import net.lecigne.coronamailsender.service.CoronaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public CoronaInfo getCoronaInfo() {
        log.info("GET /corona - Envoi des informations relative au COVID-19");
        return coronaInfoService.getCoronaInfo().get();
    }

}
