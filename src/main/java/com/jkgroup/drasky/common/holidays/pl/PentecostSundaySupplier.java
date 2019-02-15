package com.jkgroup.drasky.common.holidays.pl;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.function.Supplier;

public class PentecostSundaySupplier implements Supplier<MonthDay> {

    private Year year;

    public PentecostSundaySupplier(){
    }

    public PentecostSundaySupplier(Year year){
        this.year = year;
    }

    @Override
    public MonthDay get() {
        MonthDay easterSunday = new EasterSundaySupplier(getYear()).get();
        LocalDate pentecostDate = LocalDate.of(getYear().getValue(), easterSunday.getMonth(), easterSunday.getDayOfMonth())
                .plusWeeks(7);

        return MonthDay.of(pentecostDate.getMonth(), pentecostDate.getDayOfMonth());
    }

    private Year getYear(){
        if(this.year == null){
            return Year.now();
        }

        return this.year;
    }
}
