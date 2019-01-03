package com.jkgroup.drasky.intent

import com.jkgroup.drasky.intent.CalendarUtil
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class CalendarUtilTests extends Specification{

    @Unroll
    def "should check if next Sunday is last Sunday in current month"(){
        when:
        def result = CalendarUtil.isLastSundayInMonth(date)

        then:
        result == expectedResult

        where:
        date                                              | expectedResult
        LocalDate.of(2018, 10, 21) | false // not last Sunday
        LocalDate.of(2018, 10, 1)  | false // first day in a month
        LocalDate.of(2018, 10, 7)  | false // first Sunday
        LocalDate.of(2018, 10, 17) | false // random day
        LocalDate.of(2018, 10, 28) | true // last Sunday
        LocalDate.of(2018, 10, 30) | false // after last Sunday
        LocalDate.of(2018, 10, 26) | true // random day in a week before last Sunday
    }

    @Unroll
    def "should get local datetime from now - duration=#duration, date=#date, time=#time"(){
        given:
        def epsilon = 10 // seconds before and after a date

        when:
        def result = CalendarUtil.getLocalDateTimeFromNowOrDefault(duration, date, time)

        then:
        result <= expectedResult.plusSeconds(epsilon)

        where:
        duration     | date           | time         | expectedResult
        dur(2) | date(2018,1,1) | time(10, 20) | LocalDateTime.now().plusHours(2)
        null         | null           | null         | LocalDateTime.now()
        null         | date(2018,1,1) | time(10, 20) | LocalDateTime.of(2018,1,1,10,20)
        null         | null           | time(10, 20) | LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 20))
    }

    private Duration dur(int hours) {
        Duration.ofHours(hours)
    }

    private LocalDate date(int y, int m, int d) {
        LocalDate.of(y, m, d)
    }

    private LocalTime time(int h, int m) {
        LocalTime.of(h, m)
    }

    private LocalDateTime dateTime(int y, int m, int d, int h, long min){
        LocalDateTime.of(y,m,d,h,min)
    }
}
