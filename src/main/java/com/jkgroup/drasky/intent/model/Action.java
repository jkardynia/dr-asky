package com.jkgroup.drasky.intent.model;

import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.dto.DialogFlowResponse;

public interface Action {
    String getName();
    DialogFlowResponse execute(DialogFlowRequest request);
}
