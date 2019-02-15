package com.jkgroup.drasky.common.holidays.pl

import spock.lang.Specification
import spock.lang.Unroll

import java.time.MonthDay
import java.time.Year

class EasterSundaySupplierTest extends Specification{

    @Unroll
    def "should get correct date in #year"(){
        given:
        def dateSupplier = new EasterSundaySupplier(Year.of(year))

        when:
        def result = dateSupplier.get();

        then:
        result == expectedResult

        where:
        year | expectedResult
        2018 | MonthDay.of(4, 1)
        2017 | MonthDay.of(4, 16)
    }
}
