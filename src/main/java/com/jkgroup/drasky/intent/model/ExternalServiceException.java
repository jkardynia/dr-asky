package com.jkgroup.drasky.intent.model;

import lombok.Getter;

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
}
