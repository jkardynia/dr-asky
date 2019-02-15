package com.jkgroup.drasky.common.holidays.pl;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.function.Supplier;

public class EasterMondaySupplier implements Supplier<MonthDay> {

    private Year year;

    public EasterMondaySupplier(){
    }

    public EasterMondaySupplier(Year year){
        this.year = year;
    }

    @Override
    public MonthDay get() {
        MonthDay easterSunday = new EasterSundaySupplier(getYear()).get();
        LocalDate easterMondayDate = LocalDate.of(getYear().getValue(), easterSunday.getMonth(), easterSunday.getDayOfMonth())
                .plusDays(1);

        return MonthDay.of(easterMondayDate.getMonth(), easterMondayDate.getDayOfMonth());
    }

    private Year getYear(){
        if(this.year == null){
            return Year.now();
        }

        return this.year;
    }
}
