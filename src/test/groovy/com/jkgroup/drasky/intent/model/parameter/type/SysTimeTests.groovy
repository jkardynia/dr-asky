package com.jkgroup.drasky.intent.model.parameter.type


import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalTime
import static com.jkgroup.drasky.fixtures.IntentRequestFactory.*

class SysTimeTests extends Specification{

    @Unroll
    def "test for parameter value='#value'"(){
        given:
        def sysTime = new SysTime()
        def paramName = 'sys_date_param_name'
        def request = requestWithParams([(paramName) : value, "any_other" : "some value"])

        when:
        def result = sysTime.getValue(request, paramName)

        then:
        result == expectedResult

        where:
        value                       | expectedResult
        "2011-12-03T10:15:30+01:00" | Optional.of(LocalTime.of(10, 15, 30))
        "2011-12-03T23:00:00+05:00" | Optional.of(LocalTime.of(23, 00, 00))
        "2011-12-03T23:00:00-05:00" | Optional.of(LocalTime.of(23, 00, 00))
        ""                          | Optional.empty()
        null                        | Optional.empty()
        new HashMap<>()             | Optional.empty()
    }
}
