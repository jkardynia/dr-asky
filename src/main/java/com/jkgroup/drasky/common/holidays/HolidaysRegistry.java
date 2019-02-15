package com.jkgroup.drasky.common.holidays;

import java.time.Year;
import java.util.List;
import java.util.Locale;

public interface HolidaysRegistry {
    List<Holiday> getAll(Year year);
    Locale getLocale();
}
