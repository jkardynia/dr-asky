package com.jkgroup.drasky.intent.model.parameter.type

import com.jkgroup.drasky.intent.dto.DialogFlowRequest
import com.jkgroup.drasky.intent.dto.QueryResultDto
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalTime

class SysTimeTests extends Specification{

    @Unroll
    def "test for parameter value='#value'"(){
        given:
        def sysTime = new SysTime()
        def paramName = 'sys_date_param_name'
        def request = request([(paramName) : value, "any_other" : "some value"])

        when:
        def result = sysTime.getValue(request, paramName)

        then:
        result == expectedResult

        where:
        value                       | expectedResult
        "2011-12-03T10:15:30+01:00" | LocalTime.of(10, 15, 30)
        "2011-12-03T23:00:00+05:00" | LocalTime.of(23, 00, 00)
        "2011-12-03T23:00:00-05:00" | LocalTime.of(23, 00, 00)
    }

    @Unroll
    def "test for parameter value='#value' should result on NOW"(){
        given:
        def sysTime = new SysTime()
        def paramName = 'sys_date_param_name'
        def request = request([(paramName) : value, "any_other" : "some value"])

        when:
        def result = sysTime.getValue(request, paramName)

        then:
        result > expectedResult
        result <= LocalTime.now()

        where:
        value                       | expectedResult
        ""                          | LocalTime.now().minusSeconds(30)
        null                        | LocalTime.now().minusSeconds(30)
        new HashMap<>()             | LocalTime.now().minusSeconds(30)

    }

    private DialogFlowRequest request(Map<String, Object> parameters){
        new DialogFlowRequest("", "", new QueryResultDto("", "", parameters, null, null, [], [], null, null, null, null, null))
    }
}
