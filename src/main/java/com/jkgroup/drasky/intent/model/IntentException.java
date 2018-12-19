package com.jkgroup.drasky.intent.model;

public class IntentException extends RuntimeException{
    public IntentException(String message) {
        super(message);
    }

    public static IntentException profileHomeLocationNotSet(String profileName){
        return new IntentException("Home location for profile " + profileName + " is not specified.");
    }

    public static IntentException intentActionNotFound(String actionName){
        return new IntentException("No action registered for intent action " + actionName);
    }
}
