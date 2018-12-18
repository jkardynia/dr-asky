package com.jkgroup.drasky.intent.model;

import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.dto.DialogFlowResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class IntentActionRouter {

    private Map<String, IntentAction> intentActions;

    @Autowired
    public IntentActionRouter(List<IntentAction> intentActions){
        this.intentActions = intentActions.stream()
                .collect(Collectors.toMap(IntentAction::getName, Function.identity()));
    }

    public DialogFlowResponse executeAction(DialogFlowRequest request){
        String actionName = request.getQueryResult().getAction();
        IntentAction action = intentActions.get(actionName);

        if(action == null){
            throw new RuntimeException("No action registered for intent " + actionName);
        }

        return action.execute(request);
    }
}
