package com.systemedz.energydata.api.controllers;

import com.systemedz.energydata.application.EntsoeService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/entsoe")
@AllArgsConstructor
public class EntsoeController {

    private EntsoeService service;

    @GetMapping(value = "/api", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getEntsoeData(@RequestParam Map<String, String> params) {
        String apiResponse = service.getEntsoeData(params);
        return Strings.isNotBlank(apiResponse) ? ResponseEntity.ok(apiResponse) : ResponseEntity.noContent().build();
    }
}
