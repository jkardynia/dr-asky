package com.jkgroup.drasky.common.holidays.pl;

import com.jkgroup.drasky.common.holidays.Holiday;
import com.jkgroup.drasky.common.holidays.HolidaysRegistry;

import java.time.Year;
import java.util.List;
import java.util.Locale;

public class PolishHolidayRegistry implements HolidaysRegistry {

    @Override
    public List<Holiday> getAll(Year year) {
        return PolishHoliday.valuesInYear(year);
    }

    @Override
    public Locale getLocale() {
        return Locale.forLanguageTag("pl-PL"); //todo should validate for correct locales
    }
}
