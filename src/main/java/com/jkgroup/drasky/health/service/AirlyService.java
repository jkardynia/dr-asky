package com.jkgroup.drasky.health.service;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlyService {
    public AirQualityInfo checkAirQuality(String location){
        return new AirQualityInfo(getRandomQualityInfo());
    }

    private String getRandomQualityInfo(){
        List<String> possibleOutputs = Lists.newArrayList("good", "medium", "bad");

        return possibleOutputs.get((int)Math.floor(Math.random()*possibleOutputs.size()));
    }
}
