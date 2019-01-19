package com.jkgroup.drasky.common.holidays.pl;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.function.Supplier;

public class EasterMondaySupplier implements Supplier<MonthDay> {
    @Override
    public MonthDay get() {
        MonthDay easterSunday = new EasterSundaySupplier().get();
        LocalDate easterMondayDate = LocalDate.of(Year.now().getValue(), easterSunday.getMonth(), easterSunday.getDayOfMonth())
                .plusDays(1);

        return MonthDay.of(easterMondayDate.getMonth(), easterMondayDate.getDayOfMonth());
    }
}
