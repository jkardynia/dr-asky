package com.jkgroup.drasky.intent.model.parameter.type

import com.jkgroup.drasky.intent.dto.DialogFlowRequest
import com.jkgroup.drasky.intent.dto.QueryResultDto
import spock.lang.Specification
import spock.lang.Unroll

class SysDurationTests extends Specification{

    @Unroll
    def "test for parameter value='#value'"(){
        given:
        def sysDuration = new SysDuration()
        def paramName = 'sys_date_param_name'
        def request = request([(paramName) : value, "any_other" : "some value"])

        when:
        def result = sysDuration.getValue(request, paramName)

        then:
        result == expectedResult

        where:
        value                                   | expectedResult
        ["amount": 20, "unit": "s"]             | java.time.Duration.ofSeconds(20)
        ["amount": 20, "unit": "min"]           | java.time.Duration.ofMinutes(20)
        ["amount": 20, "unit": "h"]             | java.time.Duration.ofHours(20)
        ["amount": 20, "unit": "day"]           | java.time.Duration.ofDays(20)
        ["amount": 20.5, "unit": "s"]           | java.time.Duration.ofSeconds(20)
        ""                                      | java.time.Duration.ZERO
        null                                    | java.time.Duration.ZERO
        [:]                                     | java.time.Duration.ZERO
        "20s"                                   | java.time.Duration.ZERO
        ["wrongName": "wrongValue"]             | java.time.Duration.ZERO
        ["amount": 20, "unit": "wrong"]         | java.time.Duration.ZERO
        ["amount": "not_a_number", "unit": "s"] | java.time.Duration.ZERO
        ["amount": "20", "unit": "s"]           | java.time.Duration.ZERO

    }

    private DialogFlowRequest request(Map<String, Object> parameters){
        new DialogFlowRequest("", "", new QueryResultDto("", "", parameters, null, null, [], [], null, null, null, null, null))
    }
}
