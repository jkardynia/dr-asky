package com.jkgroup.drasky.commuting.controller.dto;

import lombok.Getter;

@Getter
public class DialogFlowRequest {
    private String responseId;
    private String session;
    private QueryResultDto queryResult;
}
