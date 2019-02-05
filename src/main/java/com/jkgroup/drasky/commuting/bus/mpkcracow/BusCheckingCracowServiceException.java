package com.jkgroup.drasky.commuting.bus.mpkcracow;

import com.jkgroup.drasky.intent.model.ExternalServiceException;

public class BusCheckingCracowServiceException extends ExternalServiceException {
    public BusCheckingCracowServiceException(String message, Exception e){
        super("Bus checking Cracow", message, e);
    }

    public BusCheckingCracowServiceException(String message){
        super("Bus checking Cracow", message);
    }
}
