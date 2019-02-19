package com.jkgroup.drasky.integrationTests

import com.jkgroup.drasky.intent.AppConfiguration
import com.jkgroup.drasky.intent.model.parameter.Duration

import java.time.Month
import java.time.ZonedDateTime

class IntentRequests {

    static String getFindBusRequest(String destination, Duration duration, ZonedDateTime dateTime){
        return "{\n" +
                "  \"responseId\": \"01f078c6-1598-4281-ac26-6f2d19e56a26\",\n" +
                "  \"queryResult\": {\n" +
                "    \"queryText\": \"bus to office after 2 hours\",\n" +
                "    \"action\": \"find_bus\",\n" +
                "    \"parameters\": {\n" +
                "      \"destination\": \""+ destination +"\"\n" +

                getDuration(duration).orElse("") +
                getDateTime(dateTime).orElse("") +

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

    static String getCheckAirQualityRequest(String location){
        return "{\n" +
                "  \"responseId\": \"01f078c6-1598-4281-ac26-6f2d19e56a26\",\n" +
                "  \"queryResult\": {\n" +
                "    \"queryText\": \"check air quality at office\",\n" +
                "    \"action\": \"air_quality\",\n" +
                "    \"parameters\": {\n" +
                "      \"location\": \""+ location +"\"\n" +
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

    static String getIsMerchantSundayRequest(){
        return "{\n" +
                "  \"responseId\": \"01f078c6-1598-4281-ac26-6f2d19e56a26\",\n" +
                "  \"queryResult\": {\n" +
                "    \"queryText\": \"bus to office after 2 hours\",\n" +
                "    \"action\": \"merchant-sunday\",\n" +
                "    \"parameters\": {},\n" +
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

    static String getInvalidActionRequest(){
        return "{\n" +
                "  \"responseId\": \"01f078c6-1598-4281-ac26-6f2d19e56a26\",\n" +
                "  \"queryResult\": {\n" +
                "    \"queryText\": \"bus to office after 2 hours\",\n" +
                "    \"action\": \"not-supported-action\",\n" +
                "    \"parameters\": {},\n" +
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

    static String getCheckHolidaysRequest(Month month){
        return "{\n" +
                "  \"responseId\": \"01f078c6-1598-4281-ac26-6f2d19e56a26\",\n" +
                "  \"queryResult\": {\n" +
                "    \"queryText\": \"check holidays\",\n" +
                "    \"action\": \"holidays-check\",\n" +
                "    \"parameters\": {\n" +

                getMonth(month).orElse("") +

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

    private static Optional<String> getDuration(Duration duration){
        if(duration == null){
            return Optional.empty()
        }

        String durationString = ",      \"duration\": {\n" +
                "        \"amount\": " + duration.amount + ",\n" +
                "        \"unit\": \"" + duration.unit + "\"\n" +
                "      }\n"

        return Optional.of(durationString)
    }

    private static Optional<String> getDateTime(ZonedDateTime dateTime){
        if(dateTime == null){
            return Optional.empty()
        }

        def timeParsed = ZonedDateTime.now().withHour(dateTime.getHour()).withMinute(dateTime.getMinute()).format(AppConfiguration.DATE_TIME_FORMATTER)

        def dateParsed = dateTime.withHour(12).withMinute(0).withSecond(0).format(AppConfiguration.DATE_TIME_FORMATTER)

        String dateTimeString = ",      \"time\": \""+ timeParsed +"\",\n" +
                "      \"date\": \"" + dateParsed + "\"\n"

        return Optional.of(dateTimeString)
    }

    private static Optional<String> getMonth(Month month){
        if(month == null){
            return Optional.empty()
        }

        def startDate = ZonedDateTime.now().withMonth(month.getValue()).withDayOfMonth(1)
        def endDate = ZonedDateTime.now().withMonth(month.getValue()).withDayOfMonth(startDate.toLocalDate().lengthOfMonth())

        String monthString = "      \"month\": {\n" +
                "        \"startDate\": \"" + startDate + "\",\n" +
                "        \"endDate\": \"" + endDate + "\"\n" +
                "      }\n"

        return Optional.of(monthString)
    }
}
