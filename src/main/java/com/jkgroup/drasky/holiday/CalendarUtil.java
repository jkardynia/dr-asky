package com.jkgroup.drasky.holiday;

import java.time.LocalDate;

import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

public class CalendarUtil {
    public static boolean isLastSundayInMonth(LocalDate from) {
        final LocalDate nextSunday = from.with(nextOrSame(SUNDAY));
        final LocalDate monthLastSunday = from.with(lastDayOfMonth())
                .with(previousOrSame(SUNDAY));

        return nextSunday.equals(monthLastSunday);
    }
}
