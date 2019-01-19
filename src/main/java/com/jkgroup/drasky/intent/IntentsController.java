package com.jkgroup.drasky.intent;

import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.dto.DialogFlowResponse;
import com.jkgroup.drasky.intent.model.IntentActionRouter;
import com.jkgroup.drasky.intent.model.IntentClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("dr-asky")
@Slf4j
public class IntentsController {

    private IntentActionRouter intentActionRouter;
    private MessageSource messageSource;

    @Autowired
    public IntentsController(IntentActionRouter intentActionRouter, MessageSource messageSource){
        this.intentActionRouter = intentActionRouter;
        this.messageSource = messageSource;
    }

    @PostMapping("agent")
    public DialogFlowResponse executeActionForIntent(@RequestBody DialogFlowRequest request, Locale locale) {
        try {
            return intentActionRouter.executeAction(request);
        }catch(IntentClientException e){
            log.error(e.getMessage(), e);

            return DialogFlowResponse
                    .builder()
                    .fulfillmentText(messageSource.getMessage(e.getTranslationKey(),
                            e.getParameters().values().toArray(),
                            getGenericErrorMessage(locale),
                            locale))
                    .build();
        }
    }

    private String getGenericErrorMessage(Locale locale){
        Map<Locale, String> genericErrorMessages = new HashMap<>();
        genericErrorMessages.put(Locale.ENGLISH, "Something went wrong.");
        genericErrorMessages.put(Locale.forLanguageTag("pl"), "Coś poszło nie tak.");

        return genericErrorMessages.getOrDefault(locale, genericErrorMessages.get(Locale.ENGLISH));
    }
}
