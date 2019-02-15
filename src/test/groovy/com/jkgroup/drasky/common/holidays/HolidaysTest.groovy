package com.jkgroup.drasky.common.holidays

import com.jkgroup.drasky.common.holidays.pl.PolishHoliday
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.YearMonth

class HolidaysTest extends Specification{

    @Unroll
    def "check if date=#localDate is holiday"(){
        given:
        def holidays = Holidays.getDefault()

        when:
        def result = holidays.isHoliday(Locale.forLanguageTag("pl-PL"), localDate)

        then:
        expectedResult == result

        where:
        localDate                                        | expectedResult
        LocalDate.of(2018, 5, 1)  | true
        LocalDate.of(2018, 5, 2)  | false
    }

    def "exception should be thrown for unsupported locale for isHoliday"(){
        given:
        def holidays = Holidays.getDefault()

        when:
        holidays.getHoliday(Locale.forLanguageTag("ru-RU"), LocalDate.of(2018, 5, 1))

        then:
        thrown(HolidayException)
    }

    @Unroll
    def "get holiday for date=#localDate"(){
        given:
        def holidays = Holidays.getDefault()

        when:
        def result = holidays.getHoliday(Locale.forLanguageTag("pl-PL"), localDate)

        then:
        expectedResult == result

        where:
        localDate                                        | expectedResult
        LocalDate.of(2018, 5, 1)  | Optional.of(PolishHoliday.MAY_DAY)
        LocalDate.of(2018, 5, 2)  | Optional.empty()
    }

    def "exception should be thrown for unsupported locale for getHoliday"(){
        given:
        def holidays = Holidays.getDefault()

        when:
        holidays.getHoliday(Locale.forLanguageTag("ru-RU"), LocalDate.of(2018, 5, 1))

        then:
        thrown(HolidayException)
    }

    @Unroll
    def "get holidays between month=#yearMonth"(){
        given:
        def holidays = Holidays.getDefault()

        when:
        def result = holidays.getAllIn(Locale.forLanguageTag("pl-PL"), yearMonth)

        then:
        expectedResult == result

        where:
        yearMonth             | expectedResult
        YearMonth.of(2018, 5) | [PolishHoliday.MAY_DAY, PolishHoliday.CONSTITUTION_DAY]
        YearMonth.of(2018, 2) | []
    }

    def "exception should be thrown for unsupported locale for getAllIn"(){
        given:
        def holidays = Holidays.getDefault()

        when:
        holidays.getAllIn(Locale.forLanguageTag("ru-RU"), YearMonth.of(2018, 5))

        then:
        thrown(HolidayException)
    }
}
