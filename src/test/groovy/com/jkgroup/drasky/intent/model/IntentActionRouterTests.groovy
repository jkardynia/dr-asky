package com.jkgroup.drasky.intent.model

import com.jkgroup.drasky.intent.dto.DialogFlowRequest
import com.jkgroup.drasky.intent.dto.DialogFlowResponse
import com.jkgroup.drasky.intent.dto.QueryResultDto
import spock.lang.Specification
import spock.lang.Unroll

class IntentActionRouterTests extends Specification {

    @Unroll
    def "executing action"(){
        given:
        def intentActionRouter = new IntentActionRouter([new MyTestAction("action1", "fulflment1"),
                                                         new MyTestAction("action2", "fulflment2"),
                                                         new MyTestAction("action3", "fulflment3")])

        when:
        def result = intentActionRouter.executeAction(request(actionName))

        then:
        result.getFulfillmentText() == fulfilmentText

        where:
        actionName | fulfilmentText
        "action1"  | "fulflment1"
        "action2"  | "fulflment2"
        "action3"  | "fulflment3"
    }

    def "executing non existing action"(){
        given:
        def intentActionRouter = new IntentActionRouter([new MyTestAction("action1", "fulflment1"),
                                                         new MyTestAction("action2", "fulflment2"),
                                                         new MyTestAction("action3", "fulflment3")])

        when:
        intentActionRouter.executeAction(request("invalid_action_name"))

        then:
        thrown(IntentException)
    }

    private DialogFlowRequest request(String action){
        new DialogFlowRequest("", "", new QueryResultDto("", action, [:], null, null, [], [], null, null, null, null, null))
    }
}

class MyTestAction implements Action{

    private String name
    private String fulfillmentText

    public MyTestAction(String name, String text){
        this.name = name
        this.fulfillmentText = text
    }

    @Override
    String getName() {
        return name
    }

    @Override
    DialogFlowResponse execute(DialogFlowRequest request) {
        DialogFlowResponse.builder().fulfillmentText(fulfillmentText).build()
    }
}
