package com.jkgroup.drasky.holiday

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

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
}
