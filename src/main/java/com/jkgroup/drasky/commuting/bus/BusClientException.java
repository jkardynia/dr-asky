package com.jkgroup.drasky.commuting.bus;

import com.jkgroup.drasky.intent.model.IntentClientException;

import java.util.LinkedHashMap;
import java.util.Map;

public class BusClientException extends IntentClientException {
    private BusClientException(String message, String translationKey, Map<String, String> params) {
        super(message, translationKey, params);
    }

    private BusClientException(String message, String translationKey) {
        super(message, translationKey);
    }

    public static BusClientException busNotFound(){
        return new BusClientException("Cannot find next bus today", "BUS_NOT_FOUND_FOR_TODAY");
    }

    public static BusClientException busStopNotFound(String busNumber, String stop){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("busNumber", busNumber);
        params.put("stop", stop);

        return new BusClientException("Cannot find bus stop " + stop + " for bus " + busNumber, "BUS_STOP_NOT_FOUND", params);
    }

    public static BusClientException busDirectionNotFound(String busNumber, String direction){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("busNumber", busNumber);
        params.put("direction", direction);

        return new BusClientException("Cannot find bus direction " + direction + " for bus " + busNumber, "BUS_DIRECTION_NOT_FOUND", params);
    }
}
