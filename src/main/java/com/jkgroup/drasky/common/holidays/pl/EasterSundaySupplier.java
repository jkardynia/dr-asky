package com.jkgroup.drasky.common.holidays.pl;


import java.time.MonthDay;
import java.time.Year;
import java.util.function.Supplier;

public class EasterSundaySupplier implements Supplier<MonthDay> {

    private Year year;

    public EasterSundaySupplier(){
    }

    public EasterSundaySupplier(Year year){
        this.year = year;
    }

    @Override
    public MonthDay get() {
        int year = getYear().getValue();
        int yearMod19 = year % 19;
        int numberOfCenturies = year / 100;
        int numberOfYearsInCurrentCentury = year % 100;
        int numberOfCenturiesDiv4 = numberOfCenturies / 4;
        int numberOfCenturiesMod4 = numberOfCenturies % 4;
        int calculation1 = (8 * numberOfCenturies + 13) / 25;
        int calculation2 = (19 * yearMod19 + numberOfCenturies - numberOfCenturiesDiv4 - calculation1 + 15) % 30;
        int numberOfYearsInCurrentCenturyDiv4 = numberOfYearsInCurrentCentury / 4;
        int numberOfYearsInCurrentCenturyMod4 = numberOfYearsInCurrentCentury % 4;
        int calculation3 = (yearMod19 + 11 * calculation2) / 319;
        int calculation4 = (2 * numberOfCenturiesMod4 + 2 * numberOfYearsInCurrentCenturyDiv4 - numberOfYearsInCurrentCenturyMod4 - calculation2 + calculation3 + 32) % 7;
        int month = (calculation2 - calculation3 + calculation4 + 90) / 25;
        int day = (calculation2 - calculation3 + calculation4 + month + 19) % 32;

        return MonthDay.of(month, day);
    }

    private Year getYear(){
        if(this.year == null){
            return Year.now();
        }

        return this.year;
    }
}
