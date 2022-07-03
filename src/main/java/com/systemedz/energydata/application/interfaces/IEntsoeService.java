package com.systemedz.energydata.application.interfaces;

import java.util.Map;

public interface IEntsoeService {

    String getEntsoeData(Map<String,String> params);
    String getDataByEventDate(Map<String,String> params);
    String getDataByPeriod(Map<String,String> params);
}
