package com.jkgroup.drasky.intent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DialogFlowRequest {
    private String responseId;
    private String session;
    private QueryResultDto queryResult;
}
