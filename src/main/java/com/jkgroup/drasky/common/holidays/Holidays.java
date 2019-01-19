package com.jkgroup.drasky.common.holidays;

import com.google.common.collect.Lists;
import com.jkgroup.drasky.common.holidays.pl.PolishHolidayRegistry;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Holidays {

    private Map<Locale, List<Holiday>> holidays;

    public Holidays(List<HolidaysRegistry> holidaysRegistries){
        holidays = holidaysRegistries.stream()
                .collect(Collectors.toMap(HolidaysRegistry::getLocale, HolidaysRegistry::getAll));
    }

    public static Holidays getDefault(){
        return new Holidays(Lists.newArrayList(new PolishHolidayRegistry()));
    }

    public boolean isHoliday(Locale locale, LocalDate date){
        validateIfLocaleIsSupported(locale);

        if(holidays.get(locale).contains(MonthDay.of(date.getMonth(), date.getDayOfMonth()))){
            return true;
        }

        return false;
    }

    public Optional<Holiday> getHoliday(Locale locale, LocalDate date){
        validateIfLocaleIsSupported(locale);

        return holidays.get(locale).stream()
                .filter(day -> day.getMothDay().equals(MonthDay.of(date.getMonth(), date.getDayOfMonth())))
                .findFirst();
    }

    public List<Holiday> getAllBetween(Locale locale, MonthDay from, MonthDay to){
        validateIfLocaleIsSupported(locale);

        return holidays.get(locale).stream()
                .filter(isBetween(from, to))
                .collect(Collectors.toList());
    }

    public List<Holiday> getAllIn(Locale locale, Month month){
        validateIfLocaleIsSupported(locale);

        return holidays.get(locale).stream()
                .filter(day -> day.getMothDay().getMonth().equals(month))
                .collect(Collectors.toList());
    }

    private void validateIfLocaleIsSupported(Locale locale){
        if(!holidays.containsKey(locale)){
            throw HolidayException.localeNotSupported(locale);
        }
    }

    private Predicate<Holiday> isBetween(MonthDay from, MonthDay to) {
        return day -> day.getMothDay().equals(from) || day.getMothDay().equals(to) || (day.getMothDay().isAfter(from) && day.getMothDay().isBefore(to));
    }
}
