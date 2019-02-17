package com.jkgroup.drasky.commuting.bus.mpkcracow;

import com.jkgroup.drasky.commuting.bus.mpkcracow.model.BusStop;
import com.jkgroup.drasky.commuting.bus.mpkcracow.model.Direction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class BusUrlResolverImpl implements BusUrlResolver {

    public BusUrlResolverImpl(@Value("${bus-time-table.cracow.base-url}") String baseUrl){
        ConnectionSettings.BASE_URL = baseUrl;
    }

    public String resolveUrl(String busNumber, Direction direction, BusStop busStop) throws MalformedURLException {
        if(BusStop.DEFAULT.equals(busStop)) {
            return ConnectionSettings.getUrl(busNumber, direction.getId());
        }

        return ConnectionSettings.getUrl(busNumber, direction.getId(), busStop.getId());
    }

    static class ConnectionSettings{

        private static final String TIME_TABLE_PATH_PATTERN = "?linia={line_nr}__{direction_nr}__{stop_nr}";
        private static final String TIME_TABLE_DEFAULT_STOP_PATH_PATTERN = "?linia={line_nr}__{direction_nr}";

        private static String BASE_URL;

        private static String getUrl(String busNumber, String dirId, String busStopId) throws MalformedURLException {
            return new URL(new URL(BASE_URL), TIME_TABLE_PATH_PATTERN
                    .replace("{line_nr}", busNumber)
                    .replace("{direction_nr}", dirId)
                    .replace("{stop_nr}", busStopId)).toString();
        }

        private static String getUrl(String busNumber, String directionNumber) throws MalformedURLException {
            return new URL(new URL(BASE_URL), TIME_TABLE_DEFAULT_STOP_PATH_PATTERN
                    .replace("{line_nr}", busNumber)
                    .replace("{direction_nr}", directionNumber)).toString();
        }
    }
}
