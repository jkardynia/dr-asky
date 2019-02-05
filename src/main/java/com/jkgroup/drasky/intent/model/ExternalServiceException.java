package com.jkgroup.drasky.intent.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ExternalServiceException extends RuntimeException {
    private String serviceName;

    public ExternalServiceException(String serviceName, String message, Exception e){
        super(message, e);
        this.serviceName = serviceName;
    }

    public ExternalServiceException(String serviceName, String message){
        super(message);
        this.serviceName = serviceName;
    }

    public static IntentClientException notSupported(String location){
        Map<String, String> params = new HashMap<>();
        params.put("location", location);

        return new IntentClientException("This service is not supported in specified location", "SERVICE_IN_LOCATION_NOT_SUPPORTED_EXCEPTION", params);
    }
}
