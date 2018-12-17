package com.jkgroup.drasky.intent;

import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.dto.DialogFlowResponse;
import com.jkgroup.drasky.intent.model.IntentActionRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dr-asky")
public class IntentsController {

    private IntentActionRouter intentActionRouter;

    @Autowired
    public IntentsController(IntentActionRouter intentActionRouter){
        this.intentActionRouter = intentActionRouter;
    }

    @PostMapping("agent")
    public DialogFlowResponse executeActionForIntent(@RequestBody DialogFlowRequest request) {
        return intentActionRouter.executeAction(request);
    }
}
