package com.jkgroup.drasky.fixtures

import com.jkgroup.drasky.intent.dto.DialogFlowRequest
import com.jkgroup.drasky.intent.dto.QueryResultDto

class IntentRequestFactory {
    static DialogFlowRequest requestWithParams(Map<String, Object> params){
        new DialogFlowRequest("", "", new QueryResultDto("", "", params, null, null, [], [], null, null, null, null, null))
    }

    static DialogFlowRequest request(String action){
        new DialogFlowRequest("", "", new QueryResultDto("", action, [:], null, null, [], [], null, null, null, null, null))
    }
}
