package com.jkgroup.drasky.commuting.bus.mpkcracow;

import com.jkgroup.drasky.commuting.bus.BusClientException;
import com.jkgroup.drasky.commuting.bus.mpkcracow.model.BusStop;
import com.jkgroup.drasky.commuting.bus.mpkcracow.model.Direction;
import com.jkgroup.drasky.commuting.bus.mpkcracow.model.Page;
import com.jkgroup.drasky.intent.AppConfiguration;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@CacheConfig(cacheNames = MpkCracowConnector.CACHE_NAME, cacheManager = AppConfiguration.NOT_EXPIRING_CACHE_BEAN_NAME)
public class MpkCracowConnector {

    public static final String CACHE_NAME = "MpkCracowConnector-cache";

    private JsoupClient client;
    private BusUrlResolver busUrlResolver;

    @Autowired
    public MpkCracowConnector(JsoupClient client, BusUrlResolver busUrlResolver) {
        this.client = client;
        this.busUrlResolver = busUrlResolver;
    }

    public Page gotoMpkWelcomeBusPage(String busNumber){
        return gotoMpkBusPage(busNumber, Direction.DEFAULT, BusStop.DEFAULT);
    }

    public Page gotoMpkBusPage(String busNumber, Direction direction, BusStop busStop){
        try {
            String url = busUrlResolver.resolveUrl(busNumber, direction, busStop);

            Document document = client.get(url, getCookies());

            return new Page(document, busNumber, direction, busStop);
        } catch (IOException e) {
            throw new BusCheckingCracowServiceException(e.getMessage(), e);
        }
    }

    @Cacheable
    public Page gotoMpkBusPage(String busNumber, String direction, String stop) {
        Direction dir = gotoMpkWelcomeBusPage(busNumber).getDirectionByName(direction)
                .orElseThrow(() -> BusClientException.busDirectionNotFound(busNumber, direction));
        BusStop busStop = gotoMpkBusPage(busNumber, dir, BusStop.DEFAULT).getBusStopByName(stop)
                .orElseThrow(() -> BusClientException.busStopNotFound(busNumber, stop));

        return gotoMpkBusPage(busNumber, dir, busStop);
    }

    private static Map<String, String> getCookies() {
        Map<String, String> cookies = new HashMap<>();
        cookies.put("ROZKLADY_AB", "0");
        cookies.put("ROZKLADY_WIDTH", "200");
        cookies.put("ROZKLADY_JEZYK", "EN");

        return cookies;
    }
}
