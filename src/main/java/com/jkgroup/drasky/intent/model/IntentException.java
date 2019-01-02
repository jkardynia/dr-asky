package com.jkgroup.drasky.intent.model;

public class IntentException extends RuntimeException{
    public IntentException(String message) {
        super(message);
    }

    public static IntentException profileLocationNotSet(String profileName){
        return new IntentException("Location for profile " + profileName + " is not defined.");
    }

    public static IntentException locationNotSet(String locationName){
        return new IntentException("Location " + locationName + " is unknown. Consider adding this location.");
    }

    public static IntentException intentActionNotFound(String actionName){
        return new IntentException("No action registered for intent action " + actionName);
    }

    public static IntentException mandatoryParameterIsMissing(String paramName){
        return new IntentException("Parameter is missing - " + paramName);
    }
}
