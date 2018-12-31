package com.jkgroup.drasky.intent.model.parameter.type

import com.jkgroup.drasky.intent.dto.DialogFlowRequest
import com.jkgroup.drasky.intent.dto.QueryResultDto
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class SysDateTests extends Specification{

    @Unroll
    def "test for parameter value='#value'"(){
        given:
        def sysDate = new SysDate()
        def paramName = 'sys_date_param_name'
        def request = request([(paramName) : value, "any_other" : "some value"])

        when:
        def result = sysDate.getValue(request, paramName)

        then:
        result == expectedResult

        where:
        value                       | expectedResult
        "2011-12-03T10:15:30+01:00" | LocalDate.of(2011, 12, 03)
        "2011-12-03T23:00:00+05:00" | LocalDate.of(2011, 12, 03)
        "2011-12-03T23:00:00-05:00" | LocalDate.of(2011, 12, 03)
        ""                          | LocalDate.now()
        null                        | LocalDate.now()
        new HashMap<>()             | LocalDate.now()

    }

    private DialogFlowRequest request(Map<String, Object> parameters){
        new DialogFlowRequest("", "", new QueryResultDto("", "", parameters, null, null, [], [], null, null, null, null, null))
    }
}
