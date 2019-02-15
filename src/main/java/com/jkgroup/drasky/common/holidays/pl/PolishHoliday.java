package com.jkgroup.drasky.common.holidays.pl;

import com.google.common.collect.Lists;
import com.jkgroup.drasky.common.holidays.Holiday;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.MonthDay;
import java.time.Year;
import java.util.List;
import java.util.function.Supplier;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PolishHoliday implements Holiday {
    NEW_YEAR("New Year's Day", () -> MonthDay.of(1, 1)),
    EPIPHANY("Epiphany",() -> MonthDay.of(1, 6)),
    EASTER_SUNDAY("Easter Sunday", new EasterSundaySupplier()),
    EASTER_MONDAY("Easter Monday", new EasterMondaySupplier()),
    MAY_DAY("May Day", () -> MonthDay.of(5, 1)),
    CONSTITUTION_DAY("Constitution Day", () -> MonthDay.of(5, 3)),
    PENTECOST_SUNDAY("Pentecost Sunday", new PentecostSundaySupplier()),
    CORPUS_CHRISTI("Corpus Christi", new CorpusChristiSupplier()),
    ASSUMPTION("Assumption of the Blessed Virgin Mary", () -> MonthDay.of(8, 15)),
    ALL_SAINTS_DAY("All Saints' Day", () -> MonthDay.of(11, 1)),
    INDEPENDENCE_DAY("Independence Day", () -> MonthDay.of(11, 11)),
    CHRISTMAS_DAY("Christmas Day", () -> MonthDay.of(12, 25)),
    SECOND_DAY_OF_CHRISTMASTIDE("Second Day of Christmastide", () -> MonthDay.of(12, 26));

    @Getter
    private String name;
    private Supplier<MonthDay> monthDaySupplier;

    public MonthDay getMonthDay(){
        return monthDaySupplier.get();
    }

    public static List<Holiday> valuesInYear(Year year){
        return  Lists.newArrayList(PolishHoliday.values()); // todo support year
    }
}
