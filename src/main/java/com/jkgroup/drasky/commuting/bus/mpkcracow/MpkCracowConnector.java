package com.jkgroup.drasky.commuting.bus.mpkcracow;

import com.jkgroup.drasky.commuting.bus.BusClientException;
import com.jkgroup.drasky.commuting.bus.mpkcracow.model.BusStop;
import com.jkgroup.drasky.commuting.bus.mpkcracow.model.Direction;
import com.jkgroup.drasky.commuting.bus.mpkcracow.model.Page;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class MpkCracowConnector {

    @Autowired
    public MpkCracowConnector(@Value("${bus-time-table.cracow.base-url}") String baseUrl) {
        ConnectionSettings.BASE_URL = baseUrl;
    }

    public Page gotoMpkWelcomeBusPage(String busNumber){
        return gotoMpkBusPage(busNumber, Direction.DEFAULT, BusStop.DEFAULT);
    }

    // todo cache that, and schedule refreshing cache at 2 a.m. for day buses and at 10p.m. for night buses
    public Page gotoMpkBusPage(String busNumber, Direction direction, BusStop busStop){
        try {
            String url = resolveUrl(busNumber, direction, busStop);

            Document document = Jsoup.connect(url)
                    .cookies(ConnectionSettings.getCookies())
                    .get();

            return new Page(document, busNumber, direction, busStop);
        } catch (IOException e) {
            throw new BusCheckingCracowServiceException(e.getMessage(), e);
        }
    }

    public Page gotoMpkBusPage(String busNumber, String direction, String stop) {
        Direction dir = gotoMpkWelcomeBusPage(busNumber).getDirectionByName(direction)
                .orElseThrow(() -> BusClientException.busDirectionNotFound(busNumber, direction));
        BusStop busStop = gotoMpkBusPage(busNumber, dir, BusStop.DEFAULT).getBusStopByName(stop)
                .orElseThrow(() -> BusClientException.busStopNotFound(busNumber, stop));

        return gotoMpkBusPage(busNumber, dir, busStop);
    }

    private String resolveUrl(String busNumber, Direction direction, BusStop busStop) throws MalformedURLException {
        if(BusStop.DEFAULT.equals(busStop)) {
            return ConnectionSettings.getUrl(busNumber, direction.getId());
        }

        return ConnectionSettings.getUrl(busNumber, direction.getId(), busStop.getId());
    }

    static class ConnectionSettings{
        private static final String TIME_TABLE_PATH_PATTERN = "?linia={line_nr}__{direction_nr}__{stop_nr}";
        private static final String TIME_TABLE_DEFAULT_STOP_PATH_PATTERN = "?linia={line_nr}__{direction_nr}";

        private static String BASE_URL;

        private static Map<String, String> getCookies() {
            Map<String, String> cookies = new HashMap<>();
            cookies.put("ROZKLADY_AB", "0");
            cookies.put("ROZKLADY_WIDTH", "200");
            cookies.put("ROZKLADY_JEZYK", "EN");

            return cookies;
        }

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
