package com.jkgroup.drasky.common.holidays.pl;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.function.Supplier;

public class PentecostSundaySupplier implements Supplier<MonthDay> {
    @Override
    public MonthDay get() {
        MonthDay easterSunday = new EasterSundaySupplier().get();
        LocalDate pentecostDate = LocalDate.of(Year.now().getValue(), easterSunday.getMonth(), easterSunday.getDayOfMonth())
                .plusWeeks(7);

        return MonthDay.of(pentecostDate.getMonth(), pentecostDate.getDayOfMonth());
    }
}
