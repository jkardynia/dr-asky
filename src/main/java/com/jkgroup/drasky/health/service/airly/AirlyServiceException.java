package com.jkgroup.drasky.health.service.airly;

import com.jkgroup.drasky.intent.model.ExternalServiceException;

public class AirlyServiceException extends ExternalServiceException {
    public AirlyServiceException(String message, Exception e){
        super("Airly", message, e);
    }

    public AirlyServiceException(String message){
        super("Airly", message);
    }
}
