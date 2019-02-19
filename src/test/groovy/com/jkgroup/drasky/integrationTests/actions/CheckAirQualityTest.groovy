package com.jkgroup.drasky.integrationTests.actions

import com.jkgroup.drasky.health.service.airly.AirQuality
import com.jkgroup.drasky.health.service.airly.AirlyClient
import com.jkgroup.drasky.health.service.airly.Index
import com.jkgroup.drasky.health.service.airly.Measurement
import com.jkgroup.drasky.integrationTests.IntentRequests
import okhttp3.Request
import org.assertj.core.util.Lists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import spock.lang.Specification

import static org.hamcrest.core.StringContains.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("mock")
class CheckAirQualityTest extends Specification{

    @Autowired
    protected MockMvc mvc

    @Autowired
    private AirlyClient airlyClient;

    def "should check air quality"(){
        given:
        def request = IntentRequests.getCheckAirQualityRequest("office")

        and:
        airlyClient.nearestMeasurements(*_) >> new CallMock(new AirQuality(new Measurement(Lists.newArrayList(new Index(null,null,null,"some description", "some advice", null)))))

        when:
        def result = performRequest(request)

        then:
        result.andExpect(status().is2xxSuccessful())

        and:
        result.andExpect(jsonPath('$.fulfillmentText').value(containsString('I am checking office')))
            .andExpect(jsonPath('$.fulfillmentText').value(containsString('some description')))
            .andExpect(jsonPath('$.fulfillmentText').value(containsString('some advice')))
    }

    //todo @Bug
    def "should return error message if location is invalid"(){
        given:
        def request = IntentRequests.getCheckAirQualityRequest("invalid_destination")

        and:
        airlyClient.nearestMeasurements(*_) >> new CallMock(new AirQuality(new Measurement(Lists.newArrayList(new Index(null,null,null,"some description", "some advice", null)))))

        when:
        def result = performRequest(request)

        then:
        result.andExpect(status().is2xxSuccessful())

        and:
        result.andExpect(jsonPath('$.fulfillmentText').value('Location invalid_destination is not set properly.'))
    }

    def "should check profile home location if location not specified"(){
        given:
        def request = IntentRequests.getCheckAirQualityRequest()

        and:
        airlyClient.nearestMeasurements(*_) >> new CallMock(new AirQuality(new Measurement(Lists.newArrayList(new Index(null,null,null,"some description", "some advice", null)))))

        when:
        def result = performRequest(request)

        then:
        result.andExpect(status().is2xxSuccessful())

        and:
        result.andExpect(jsonPath('$.fulfillmentText').value(containsString('I am checking home')))
                .andExpect(jsonPath('$.fulfillmentText').value(containsString('some description')))
                .andExpect(jsonPath('$.fulfillmentText').value(containsString('some advice')))
    }

    private ResultActions performRequest(String request) {
        mvc.perform(post('/dr-asky/agent')
                .header("Authorization", "Basic YWRtaW46YWRtaW4=") // encoded "admin" password
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
    }

    class CallMock implements Call<AirQuality>{

        private AirQuality response

        CallMock(AirQuality response){
            this.response = response;
        }

        @Override
        Response execute() throws IOException {
            return Response.success(response)
        }

        @Override
        void enqueue(Callback callback) {

        }

        @Override
        boolean isExecuted() {
            return false
        }

        @Override
        void cancel() {

        }

        @Override
        boolean isCanceled() {
            return false
        }

        @Override
        Call clone() {
            return null
        }

        @Override
        Request request() {
            return null
        }
    }
}
