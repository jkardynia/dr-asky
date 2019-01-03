package com.jkgroup.drasky.intent;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

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

    public static LocalDateTime getLocalDateTimeFromNowOrDefault(Duration duration, LocalDate date, LocalTime time) {
        return Optional.ofNullable(duration)
                .map(it -> LocalDateTime.now().plusSeconds(it.getSeconds()))
                .orElse(LocalDateTime.of(Optional.ofNullable(date).orElse(LocalDate.now()),
                        Optional.ofNullable(time).orElse(LocalTime.now())));
    }
}
