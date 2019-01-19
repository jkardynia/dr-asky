package com.jkgroup.drasky.intent;

import java.time.*;
import java.util.Arrays;
import java.util.Optional;

import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.*;

public class CalendarUtil {

    public static boolean isLastSundayInMonth(LocalDate from) {
        final LocalDate nextSunday = from.with(nextOrSame(SUNDAY));
        final LocalDate monthLastSunday = from.with(lastDayOfMonth())
                .with(previousOrSame(SUNDAY));

        return nextSunday.equals(monthLastSunday);
    }

    public static ZonedDateTime getLocalDateTimeFromNowOrDefault(Duration duration, LocalDate date, LocalTime time) {
        return Optional.ofNullable(duration)
                .map(it -> ZonedDateTime.now().plusSeconds(it.getSeconds()))
                .orElse(ZonedDateTime.of(Optional.ofNullable(date).orElse(LocalDate.now()),
                        Optional.ofNullable(time).orElse(LocalTime.now()),
                        ZoneId.systemDefault()));
    }

    public static boolean isWeekDay(LocalDateTime date){
        if(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)
                .contains(DayOfWeek.from(date))){
            return true;
        }

        return false;
    }

}
