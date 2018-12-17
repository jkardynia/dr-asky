package com.jkgroup.drasky.intent.dto;

import lombok.Getter;

@Getter
public class DialogFlowRequest {
    private String responseId;
    private String session;
    private QueryResultDto queryResult;
}
