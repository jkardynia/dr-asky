package com.jkgroup.drasky.intent.model.parameter.type

import com.jkgroup.drasky.intent.model.parameter.DatePeriod
import spock.lang.Specification
import spock.lang.Unroll

import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

class SysDatePeriodTests extends Specification{

    @Unroll
    def "test for parameter value='#value'"(){
        given:
        def sysDatePeriod = new SysDatePeriod()
        def paramName = 'sys_any_param_name'

        when:
        def result = sysDatePeriod.getValue(paramName, [(paramName) : value, "any_other" : "some value"])

        then:
        result == expectedResult

        where:
        value                                      | expectedResult
        "my value"                                 | Optional.empty()
        ""                                         | Optional.empty()
        null                                       | Optional.empty()
        [:]                                        | Optional.empty()
        ["endDate": "2011-12-03T10:15:30+01:00"]   | Optional.empty()
        ["startDate": "2011-12-03T10:15:30+01:00"] | Optional.empty()
        ["startDate": "2011-12-03T10:15:30+01:00",
         "endDate": "2011-12-04T10:12:30+01:00"]   | Optional.of(DatePeriod.of(ZonedDateTime.of(2011, 12, 3, 10, 15, 30, 0, ZoneId.ofOffset("", ZoneOffset.of("+01:00"))),
                                                        ZonedDateTime.of(2011, 12, 4, 10, 12, 30, 0, ZoneOffset.of("+01:00"))))

    }
}
