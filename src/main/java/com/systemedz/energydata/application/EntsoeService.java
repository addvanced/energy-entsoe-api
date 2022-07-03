package com.systemedz.energydata.application;

import com.systemedz.energydata.application.interfaces.IEntsoeService;
import com.systemedz.energydata.infrastructure.interfaces.IEntsoeApi;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service
@AllArgsConstructor
public class EntsoeService implements IEntsoeService {
    private IEntsoeApi entsoeApi;

    @Override
    public String getEntsoeData(Map<String,String> params) {
        if(queryDataIsValid(params)) {
            if(hasEventDateDefinition(params) || hasPeriodDefinition(params)) {
                return hasEventDateDefinition(params) ? getDataByEventDate(params) : getDataByPeriod(params);
            }
            throw new RuntimeException("Period definition is missing. Either add 'eventDate', or 'periodStart' & 'periodEnd' to URL.");
        }
        throw new RuntimeException("URL Parameters are not valid.");
    }
    @Override
    public String getDataByEventDate(Map<String, String> params) {
        var eventDate = params.get("eventDate").trim();

        if(Strings.isBlank(eventDate))
            throw new RuntimeException("No eventDate Data");

        return switch (eventDate.toLowerCase()) {
            case "lastweek" -> entsoeApi.getLastWeek(params);
            case "lastmonth" -> entsoeApi.getLastMonth(params);
            case "lastyear" -> entsoeApi.getLastYear(params);
            default -> throw new RuntimeException("invalid eventDate. Options: lastWeek, lastMonth or lastYear");
        };
    }

    @Override
    public String getDataByPeriod(Map<String, String> params) {
       return entsoeApi.getByPeriodDefinition(params);
    }

    private boolean queryDataIsValid(Map<String, String> params) {
        return params.containsKey("securityToken") && (hasEventDateDefinition(params) || hasPeriodDefinition(params));
    }

    private boolean hasEventDateDefinition(Map<String,String> queryData) {
        return queryData.keySet().contains("eventDate") && Strings.isNotBlank(queryData.get("eventDate"));
    }

    private boolean hasPeriodDefinition(Map<String,String> queryData) {
        var hasPeriodStart = queryData.keySet().contains("periodStart") && Strings.isNotBlank(queryData.get("periodStart"));
        var hasPeriodEnd = queryData.keySet().contains("periodEnd") && Strings.isNotBlank(queryData.get("periodEnd"));

        return hasPeriodStart && hasPeriodEnd;
    }
}

