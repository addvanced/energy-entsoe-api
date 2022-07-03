package com.systemedz.energydata.infrastructure;

import com.systemedz.energydata.infrastructure.interfaces.IEntsoeApi;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;

@Service
public class EntsoeApi implements IEntsoeApi {
    private final static String BASE_URL = "https://transparency.entsoe.eu/api?";

    private RestTemplate restTemplate;

    public EntsoeApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getByPeriodDefinition(Map<String,String> params) {
        return getResultByInterval(params);
    }

    @Override
    public String getLastWeek(Map<String,String> params) {
        var periodEnd = LocalDateTime.now(ZoneId.of("Europe/Copenhagen"));
        var periodStart = periodEnd.minusWeeks(1);
        addPeriodFromEventDateToParams(params, periodStart, periodEnd);

        return getResultByInterval(params);
    }

    @Override
    public String getLastMonth(Map<String,String> params) {
        var periodEnd = LocalDateTime.now(ZoneId.of("Europe/Copenhagen"));
        var periodStart = periodEnd.minusMonths(1);
        addPeriodFromEventDateToParams(params, periodStart, periodEnd);

        return getResultByInterval(params);
    }

    @Override
    public String getLastYear(Map<String,String> params) {
        var periodEnd = LocalDateTime.now(ZoneId.of("Europe/Copenhagen"));
        var periodStart = periodEnd.minusYears(1);
        addPeriodFromEventDateToParams(params, periodStart, periodEnd);

        return getResultByInterval(params);
    }

    private void addPeriodFromEventDateToParams(Map<String,String> params, LocalDateTime periodStart, LocalDateTime periodEnd) {
        params.put("periodStart", formatEntsoeDateToString(periodStart));
        params.put("periodEnd", formatEntsoeDateToString(periodEnd));
        params.remove("eventDate");
    }

    private String formatEntsoeDateToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return dateTime.format(formatter)+"2300";
    }

    private String getResultByInterval(Map<String,String> urlParams) {
        var apiUrl = addUrlParams(urlParams);
        return restTemplate.getForObject(apiUrl, String.class);
    }

    private String addUrlParams(Map<String, String> params) {
        StringBuilder result = new StringBuilder(BASE_URL);
        Iterator<String> keySet = params.keySet().iterator();
        while (keySet.hasNext()) {
            String key = keySet.next();

            result.append(key).append("=").append(params.get(key));
            if (keySet.hasNext()) {
                result.append("&");
            }
        }
        return result.toString();
    }
}
