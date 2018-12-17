package com.jkgroup.drasky.intent.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DialogFlowResponse {
    private String fulfillmentText;
    private List<FulfillmentMessageDto> fulfillmentMessages;
    private String source;
    private Object payload;
    private List<OutputContextDto> outputContexts;
    private FollowupEventInput followupEventInput;
}
