package com.jkgroup.drasky.intent;

import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.dto.DialogFlowResponse;
import com.jkgroup.drasky.intent.model.ExternalServiceException;
import com.jkgroup.drasky.intent.model.IntentActionRouter;
import com.jkgroup.drasky.intent.model.IntentClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("dr-asky")
public class IntentsController {

    private IntentActionRouter intentActionRouter;
    private ClientErrorHandler clientErrorHandler;

    @Autowired
    public IntentsController(IntentActionRouter intentActionRouter, ClientErrorHandler clientErrorHandler){
        this.intentActionRouter = intentActionRouter;
        this.clientErrorHandler = clientErrorHandler;
    }

    @PostMapping("agent")
    public DialogFlowResponse executeActionForIntent(@RequestBody DialogFlowRequest request, Locale locale) {
        try {
            return intentActionRouter.executeAction(request);
        }catch(IntentClientException e){
            return clientErrorHandler.handleIntentClientException(locale, e);
        }catch(ExternalServiceException e){
            return clientErrorHandler.handleIntentClientException(locale, IntentClientException.from(e));
        }
    }
}
