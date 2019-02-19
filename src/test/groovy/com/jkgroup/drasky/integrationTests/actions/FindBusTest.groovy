package com.jkgroup.drasky.integrationTests.actions

import com.jkgroup.drasky.commuting.bus.mpkcracow.BusUrlResolver
import com.jkgroup.drasky.integrationTests.IntentRequests
import com.jkgroup.drasky.intent.model.parameter.Duration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

import static org.hamcrest.core.StringContains.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("mock")
class FindBusTest extends Specification{

    @Autowired
    protected MockMvc mvc

    @Autowired
    private BusUrlResolver busUrlResolverStub;

    def "should find a bus after specified duration"(){
        given:
        def request = IntentRequests.getFindBusRequest("office", Duration.of(2, "h"), null)

        and:
        busUrlResolverStub.resolveUrl(*_) >> "stubs/bus/cracow/get-bus-time-table.html"

        when:
        def result = performRequest(request)

        then:
        result.andExpect(status().is2xxSuccessful())

        and:
        result.andExpect(jsonPath('$.fulfillmentText').value(containsString('bus number 144')))
            .andExpect(jsonPath('$.fulfillmentText').value(containsString('1:57 PM')))
    }

    def "should find a bus after specified date time"(){
        given:
        def request = IntentRequests.getFindBusRequest("office", null,
                ZonedDateTime.of(LocalDateTime.of(2018, 2, 20, 10, 0, 0), ZoneId.of('Europe/Warsaw')))

        and:
        busUrlResolverStub.resolveUrl(*_) >> "stubs/bus/cracow/get-bus-time-table.html"

        when:
        def result = performRequest(request)

        then:
        result.andExpect(status().is2xxSuccessful())

        and:
        result.andExpect(jsonPath('$.fulfillmentText').value(containsString('bus number 144')))
                .andExpect(jsonPath('$.fulfillmentText').value(containsString('10:19 AM')))
    }

    def "should find a bus after now"(){
        given:
        def request = IntentRequests.getFindBusRequest("office", null, null)

        and:
        busUrlResolverStub.resolveUrl(*_) >> "stubs/bus/cracow/get-bus-time-table.html"

        when:
        def result = performRequest(request)

        then:
        result.andExpect(status().is2xxSuccessful())

        and:
        result.andExpect(jsonPath('$.fulfillmentText').value(containsString('bus number 144')))
                .andExpect(jsonPath('$.fulfillmentText').value(containsString('11:59 AM')))
    }

    def "should return error message if destination is invalid"(){
        given:
        def request = IntentRequests.getFindBusRequest("invalid_destination", null, null)

        and:
        busUrlResolverStub.resolveUrl(*_) >> "stubs/bus/cracow/get-bus-time-table.html"

        when:
        def result = performRequest(request)

        then:
        result.andExpect(status().is2xxSuccessful())

        and:
        result.andExpect(jsonPath('$.fulfillmentText').value('Location invalid_destination is not set properly.'))
    }

    private ResultActions performRequest(String request) {
        mvc.perform(post('/dr-asky/agent')
                .header("Authorization", "Basic YWRtaW46YWRtaW4=") // encoded "admin" password
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
    }
}
