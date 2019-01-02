package com.jkgroup.drasky.intent.model.parameter.type


import spock.lang.Specification
import spock.lang.Unroll
import static com.jkgroup.drasky.fixtures.IntentRequestFactory.*
class SysAnyTests extends Specification{

    @Unroll
    def "test for parameter value='#value'"(){
        given:
        def sysAny = new SysAny()
        def paramName = 'sys_any_param_name'
        def request = requestWithParams([(paramName) : value, "any_other" : "some value"])

        when:
        def result = sysAny.getValue(request, paramName)

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
