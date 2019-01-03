package com.jkgroup.drasky.intent.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class IntentClientException extends RuntimeException {

    private String translationKey;
    private Map<String, String> parameters;

    public IntentClientException(String message, String translationKey, Map<String, String> params){
        super(message);

        this.translationKey = translationKey;
        this.parameters = params;
    }

    public static IntentClientException locationNotSet(String locationName){
        Map<String, String> params = new HashMap<>();
        params.put("location", locationName);

        return new IntentClientException("Location " + locationName + " is unknown. Consider adding this location.",
                "INTENT_CLIENT_ERROR_LOCATION_NOT_SET",
                params);
    }
}
