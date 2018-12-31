package com.jkgroup.drasky.intent.model.parameter.type

import com.jkgroup.drasky.intent.dto.DialogFlowRequest
import com.jkgroup.drasky.intent.dto.QueryResultDto
import spock.lang.Specification
import spock.lang.Unroll

class SysAnyTests extends Specification{

    @Unroll
    def "test for parameter value='#value'"(){
        given:
        def sysAny = new SysAny()
        def paramName = 'sys_any_param_name'
        def request = request([(paramName) : value, "any_other" : "some value"])

        when:
        def result = sysAny.getValue(request, paramName)

        then:
        result == expectedResult

        where:
        value           | expectedResult
        "my value"      | "my value"
        ""              | ""
        null            | ""
        new HashMap<>() | ""

    }

    private DialogFlowRequest request(Map<String, Object> parameters){
        new DialogFlowRequest("", "", new QueryResultDto("", "", parameters, null, null, [], [], null, null, null, null, null))
    }
}
