package com.jkgroup.drasky.common.holidays;

import java.util.List;
import java.util.Locale;

public interface HolidaysRegistry {
    List<Holiday> getAll();
    Locale getLocale();
}
