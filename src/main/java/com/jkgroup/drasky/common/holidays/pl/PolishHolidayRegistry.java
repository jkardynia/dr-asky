package com.jkgroup.drasky.common.holidays.pl;

import com.google.common.collect.Lists;
import com.jkgroup.drasky.common.holidays.Holiday;
import com.jkgroup.drasky.common.holidays.HolidaysRegistry;

import java.util.List;
import java.util.Locale;

public class PolishHolidayRegistry implements HolidaysRegistry {

    @Override
    public List<Holiday> getAll() {
        return Lists.newArrayList(PolishHoliday.values());
    }

    @Override
    public Locale getLocale() {
        return Locale.forLanguageTag("pl-PL");
    }
}
