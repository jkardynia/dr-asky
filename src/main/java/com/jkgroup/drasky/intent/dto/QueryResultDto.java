package com.jkgroup.drasky.intent.dto;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class QueryResultDto {
    private String queryText;
    private String action;
    private Map<String, Object> parameters;
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
