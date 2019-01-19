package com.jkgroup.drasky.intent.model.parameter.type

import spock.lang.Specification
import spock.lang.Unroll

class SysAnyTests extends Specification{

    @Unroll
    def "test for parameter value='#value'"(){
        given:
        def sysAny = new SysAny()
        def paramName = 'sys_any_param_name'

        when:
        def result = sysAny.getValue(paramName, [(paramName) : value, "any_other" : "some value"])

        then:
        result == expectedResult

        where:
        value           | expectedResult
        "my value"      | Optional.of("my value")
        ""              | Optional.of("")
        null            | Optional.empty()
        new HashMap<>() | Optional.empty()

    }
}
