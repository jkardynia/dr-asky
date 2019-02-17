package com.jkgroup.drasky.intent

import com.jkgroup.drasky.intent.model.IntentClientException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest
@ActiveProfiles("mock")
class ClientErrorHandlerTest extends Specification{
    @Autowired
    ClientErrorHandler clientErrorHandler

    def "should get message based on exception"(){
        given:
        def exception = new IntentClientException("some error", "SOME_ERROR_TRANSLATION_KEY", [testParamName: "testParam"])

        when:
        def result = clientErrorHandler.handleIntentClientException(Locale.forLanguageTag("mock"), exception)

        then:
        result.getFulfillmentText() == "My test message with testParam"
    }

    @Unroll
    def "should get default error message locale=#locale"(){
        given:
        def exception = new IntentClientException("some error", "NOT_EXISTING_TRANSLATION_KEY", [:])

        when:
        def result = clientErrorHandler.handleIntentClientException(locale, exception)

        then:
        result.getFulfillmentText() == expectedResult

        where:
        expectedResult | locale
        "Something went wrong." | Locale.JAPANESE // not added to the code - should fallback to ENGLISH
        "Something went wrong." | Locale.ENGLISH
        "Coś poszło nie tak."   | Locale.forLanguageTag("pl")

    }
}
