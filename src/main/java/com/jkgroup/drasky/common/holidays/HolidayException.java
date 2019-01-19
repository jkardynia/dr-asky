package com.jkgroup.drasky.common.holidays;

import java.util.Locale;

public class HolidayException extends RuntimeException{

    private HolidayException(String message){
        super(message);
    }

    public static HolidayException localeNotSupported(Locale locale){
        return new HolidayException("Locale " + locale + " is not supported");
    }
}
