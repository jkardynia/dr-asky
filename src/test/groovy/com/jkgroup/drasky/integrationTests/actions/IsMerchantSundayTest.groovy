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

import static org.hamcrest.core.StringContains.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("mock")
class IsMerchantSundayTest extends Specification{

    @Autowired
    protected MockMvc mvc

    def "should check if next sunday is merchant"(){
        given:
        def request = IntentRequests.getIsMerchantSundayRequest()

        when:
        def result = performRequest(request)

        then:
        result.andExpect(status().is2xxSuccessful())

        and:
        result.andExpect(jsonPath('$.fulfillmentText').value(containsString('Next Sunday shops are open')))
    }

    private ResultActions performRequest(String request) {
        mvc.perform(post('/dr-asky/agent')
                .header("Authorization", "Basic YWRtaW46YWRtaW4=") // encoded "admin" password
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
    }
}
