package com.jkgroup.drasky.intent.model.parameter.type

import spock.lang.Specification
import spock.lang.Unroll

class SysDurationTests extends Specification{

    @Unroll
    def "test for parameter value='#value'"(){
        given:
        def sysDuration = new SysDuration()
        def paramName = 'sys_date_param_name'

        when:
        def result = sysDuration.getValue(paramName, [(paramName) : value, "any_other" : "some value"])

        then:
        result == expectedResult

        where:
        value                                   | expectedResult
        ["amount": 20, "unit": "s"]             | Optional.of(java.time.Duration.ofSeconds(20))
        ["amount": 20, "unit": "min"]           | Optional.of(java.time.Duration.ofMinutes(20))
        ["amount": 20, "unit": "h"]             | Optional.of(java.time.Duration.ofHours(20))
        ["amount": 20, "unit": "day"]           | Optional.of(java.time.Duration.ofDays(20))
        ["amount": 20.5, "unit": "s"]           | Optional.of(java.time.Duration.ofSeconds(20))
        ""                                      | Optional.empty()
        null                                    | Optional.empty()
        [:]                                     | Optional.empty()
        "20s"                                   | Optional.empty()
        ["wrongName": "wrongValue"]             | Optional.empty()
        ["amount": 20, "unit": "wrong"]         | Optional.empty()
        ["amount": "not_a_number", "unit": "s"] | Optional.empty()
        ["amount": "20", "unit": "s"]           | Optional.empty()

    }
}
