package com.jkgroup.drasky.common.holidays.pl;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.function.Supplier;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.temporal.TemporalAdjusters.*;

public class CorpusChristiSupplier implements Supplier<MonthDay> {

    @Override
    public MonthDay get() {
        MonthDay easterSunday = new EasterSundaySupplier().get();
        LocalDate pentecostDate = LocalDate.of(Year.now().getValue(), easterSunday.getMonth(), easterSunday.getDayOfMonth())
                .with(next(THURSDAY))
                .plusWeeks(8);

        return MonthDay.of(pentecostDate.getMonth(), pentecostDate.getDayOfMonth());
    }
}
