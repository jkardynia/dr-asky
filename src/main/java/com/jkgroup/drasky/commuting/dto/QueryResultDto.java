package com.jkgroup.drasky.commuting.dto;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class QueryResultDto {
    private String queryText;
    private Map<String, String> parameters;
    private Boolean allRequiredParamsPresent;
    private String fulfillmentText;
    private List<FulfillmentMessageDto> fulfillmentMessages;
    private List<OutputContextDto> outputContexts;
    private IntentDto intent;
    private Integer intentDetectionConfidence;
    private Object diagnosticInfo;
    private String languageCode;
    private Object originalDetectIntentRequest;
}
