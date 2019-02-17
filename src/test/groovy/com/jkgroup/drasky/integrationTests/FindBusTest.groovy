package com.jkgroup.drasky.integrationTests

import com.jkgroup.drasky.commuting.bus.mpkcracow.BusUrlResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.hamcrest.core.StringContains.*

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("mock")
class FindBusTest extends Specification{

    @Autowired
    protected MockMvc mvc

    @Autowired
    private BusUrlResolver busUrlResolverStub;

    def "should find a bus"(){
        given:
        def request = getRequest();

        and:
        busUrlResolverStub.resolveUrl(*_) >> "stubs/bus/cracow/get-bus-time-table.html"

        when:
        def result = mvc.perform(post('/dr-asky/agent')
                .header("Authorization", "Basic YWRtaW46YWRtaW4=") // encoded "admin" password
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))

        then:
        result.andExpect(status().is2xxSuccessful())

        and:
        result.andExpect(jsonPath('$.fulfillmentText').value(containsString('bus number 144')))
            .andExpect(jsonPath('$.fulfillmentText').value(containsString('1:57 PM')))

    }

    String getRequest(){
        return "{\n" +
                "  \"responseId\": \"01f078c6-1598-4281-ac26-6f2d19e56a26\",\n" +
                "  \"queryResult\": {\n" +
                "    \"queryText\": \"bus to office after 2 hours\",\n" +
                "    \"action\": \"find_bus\",\n" +
                "    \"parameters\": {\n" +
                "      \"destination\": \"office\",\n" +
                "      \"time\": \"\",\n" +
                "      \"duration\": {\n" +
                "        \"amount\": 2,\n" +
                "        \"unit\": \"h\"\n" +
                "      },\n" +
                "      \"date\": \"\"\n" +
                "    },\n" +
                "    \"allRequiredParamsPresent\": true,\n" +
                "    \"fulfillmentText\": \"Please repeat.\",\n" +
                "    \"fulfillmentMessages\": [\n" +
                "      {\n" +
                "        \"text\": {\n" +
                "          \"text\": [\n" +
                "            \"Please repeat.\"\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"intent\": {\n" +
                "      \"name\": \"projects/drasky-225017/agent/intents/b17cc4c1-92ce-4ba5-bdf5-36b42152ece1\",\n" +
                "      \"displayName\": \"Find bus\"\n" +
                "    },\n" +
                "    \"intentDetectionConfidence\": 1,\n" +
                "    \"languageCode\": \"en\"\n" +
                "  },\n" +
                "  \"originalDetectIntentRequest\": {\n" +
                "    \"payload\": {}\n" +
                "  },\n" +
                "  \"session\": \"projects/drasky-225017/agent/sessions/0d38b150-29d1-24ed-93f0-e0203dae9cba\"\n" +
                "}";
    }

    @TestConfiguration
    static class StubConfig {

        private DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        @Primary
        BusUrlResolver busUrlResolver() {
            return detachedMockFactory.Stub(BusUrlResolver)
        }
    }
}
