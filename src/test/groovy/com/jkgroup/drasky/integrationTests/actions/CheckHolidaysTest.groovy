package com.jkgroup.drasky.integrationTests.actions

import com.jkgroup.drasky.integrationTests.IntentRequests
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification

import java.time.Month

import static org.hamcrest.core.StringContains.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("mock")
class CheckHolidaysTest extends Specification{

    @Autowired
    protected MockMvc mvc

    def "should find holidays in specific month"(){
        given:
        def request = IntentRequests.getCheckHolidaysRequest(Month.APRIL)

        when:
        def result = performRequest(request)

        then:
        result.andExpect(status().is2xxSuccessful())

        and:
        result.andExpect(jsonPath('$.fulfillmentText').value(containsString('Number of holidays in APRIL is 2')))
    }

    def "should find holidays in current month"(){
        given:
        def request = IntentRequests.getCheckHolidaysRequest()

        when:
        def result = performRequest(request)

        then:
        result.andExpect(status().is2xxSuccessful())

        and:
        result.andExpect(jsonPath('$.fulfillmentText').value(containsString('There is no holidays in FEBRUARY')))
    }

    private ResultActions performRequest(String request) {
        mvc.perform(post('/dr-asky/agent')
                .header("Authorization", "Basic YWRtaW46YWRtaW4=") // encoded "admin" password
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
    }
}
