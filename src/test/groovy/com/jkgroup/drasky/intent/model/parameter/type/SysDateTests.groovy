package com.jkgroup.drasky.intent.model.parameter.type

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class SysDateTests extends Specification{

    @Unroll
    def "test for parameter value='#value'"(){
        given:
        def sysDate = new SysDate()
        def paramName = 'sys_date_param_name'

        when:
        def result = sysDate.getValue(paramName, [(paramName) : value, "any_other" : "some value"])

        then:
        result == expectedResult

        where:
        value                       | expectedResult
        "2011-12-03T10:15:30+01:00" | Optional.of(LocalDate.of(2011, 12, 03))
        "2011-12-03T23:00:00+05:00" | Optional.of(LocalDate.of(2011, 12, 03))
        "2011-12-03T23:00:00-05:00" | Optional.of(LocalDate.of(2011, 12, 03))
        ""                          | Optional.empty()
        null                        | Optional.empty()
        new HashMap<>()             | Optional.empty()

    }
}
