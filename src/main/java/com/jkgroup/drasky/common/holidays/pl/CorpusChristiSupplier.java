package com.jkgroup.drasky.common.holidays.pl;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.function.Supplier;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.temporal.TemporalAdjusters.*;

public class CorpusChristiSupplier implements Supplier<MonthDay> {

    private Year year;

    public CorpusChristiSupplier(){
    }

    public CorpusChristiSupplier(Year year){
        this.year = year;
    }

    @Override
    public MonthDay get() {
        MonthDay easterSunday = new EasterSundaySupplier(getYear()).get();
        LocalDate pentecostDate = LocalDate.of(getYear().getValue(), easterSunday.getMonth(), easterSunday.getDayOfMonth())
                .with(next(THURSDAY))
                .plusWeeks(8);

        return MonthDay.of(pentecostDate.getMonth(), pentecostDate.getDayOfMonth());
    }

    private Year getYear(){
        if(this.year == null){
            return Year.now();
        }

        return this.year;
    }
}
