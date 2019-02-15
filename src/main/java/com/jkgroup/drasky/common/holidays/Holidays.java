package com.jkgroup.drasky.common.holidays;

import com.google.common.collect.Lists;
import com.jkgroup.drasky.common.holidays.pl.PolishHolidayRegistry;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Holidays {

    private Map<Locale, HolidaysRegistry> holidays;

    public Holidays(List<HolidaysRegistry> holidaysRegistries){
        holidays = holidaysRegistries.stream()
                .collect(Collectors.toMap(HolidaysRegistry::getLocale, Function.identity()));
    }

    public static Holidays getDefault(){
        return new Holidays(Lists.newArrayList(new PolishHolidayRegistry()));
    }

    public boolean isHoliday(Locale locale, LocalDate date){
        validateIfLocaleIsSupported(locale);

        if(getHolidaysMonthDays(locale, Year.of(date.getYear())).contains(MonthDay.of(date.getMonth(), date.getDayOfMonth()))){
            return true;
        }

        return false;
    }

    public Optional<Holiday> getHoliday(Locale locale, LocalDate date){
        validateIfLocaleIsSupported(locale);

        return holidays.get(locale).getAll(Year.of(date.getYear())).stream()
                .filter(day -> day.getMonthDay().equals(MonthDay.of(date.getMonth(), date.getDayOfMonth())))
                .findFirst();
    }

    public List<Holiday> getAllIn(Locale locale, YearMonth yearMonth){
        validateIfLocaleIsSupported(locale);

        return holidays.get(locale).getAll(Year.of(yearMonth.getYear())).stream()
                .filter(day -> day.getMonthDay().getMonth().equals(yearMonth.getMonth()))
                .collect(Collectors.toList());
    }

    private void validateIfLocaleIsSupported(Locale locale){
        if(!holidays.containsKey(locale)){
            throw HolidayException.localeNotSupported(locale);
        }
    }

    private List<MonthDay> getHolidaysMonthDays(Locale locale, Year year) {
        return holidays.get(locale).getAll(year).stream()
                .map(it -> it.getMonthDay())
                .collect(Collectors.toList());
    }
}
