package com.systemedz.energydata.infrastructure.interfaces;

import java.util.Map;

public interface IEntsoeApi {

    String getByPeriodDefinition(Map<String,String> params);
    String getLastWeek(Map<String,String> params);

    String getLastMonth(Map<String,String> params);

    String getLastYear(Map<String,String> params);

}
