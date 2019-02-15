package com.jkgroup.drasky.common.holidays.pl

import spock.lang.Specification
import spock.lang.Unroll

import java.time.MonthDay
import java.time.Year

class CorpusChristiSupplierTest extends Specification {

    @Unroll
    def "should get correct date in #year"(){
        given:
        def dateSupplier = new CorpusChristiSupplier(Year.of(year))

        when:
        def result = dateSupplier.get();

        then:
        result == expectedResult

        where:
        year | expectedResult
        2018 | MonthDay.of(5, 31)
        2017 | MonthDay.of(6, 15)
    }
}
