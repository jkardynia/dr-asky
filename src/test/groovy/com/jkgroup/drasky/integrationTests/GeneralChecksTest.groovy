package com.jkgroup.drasky.integrationTests


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.util.NestedServletException
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("mock")
class GeneralChecksTest extends Specification{

    @Autowired
    protected MockMvc mvc

    def "should be not authorized"(){
        given:
        def request = IntentRequests.getCheckHolidaysRequest()

        when:
        def result = mvc.perform(post('/dr-asky/agent')
                .header("Authorization", "Basic WRONG=")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))

        then:
        result.andExpect(status().isUnauthorized())
    }

    def "should fail for not recognized action"(){
        given:
        def request = IntentRequests.getInvalidActionRequest()

        when:
        mvc.perform(post('/dr-asky/agent')
                .header("Authorization", "Basic YWRtaW46YWRtaW4=") // encoded "admin" password
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))

        then:
        thrown(NestedServletException)
    }
}
