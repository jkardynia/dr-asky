package com.jkgroup.drasky.intent;

import com.jkgroup.drasky.intent.dto.DialogFlowResponse;
import com.jkgroup.drasky.intent.model.IntentClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
public class ClientErrorHandler {
    private MessageSource messageSource;

    @Autowired
    public ClientErrorHandler(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    public DialogFlowResponse handleIntentClientException(Locale locale, IntentClientException e) {
        log.error(e.getMessage(), e);

        return DialogFlowResponse
                .builder()
                .fulfillmentText(messageSource.getMessage(e.getTranslationKey(),
                        e.getParameters().values().toArray(),
                        getGenericErrorMessage(locale),
                        locale))
                .build();
    }

    private String getGenericErrorMessage(Locale locale){
        Map<Locale, String> genericErrorMessages = new HashMap<>();
        genericErrorMessages.put(Locale.ENGLISH, "Something went wrong.");
        genericErrorMessages.put(Locale.forLanguageTag("pl"), "Coś poszło nie tak.");

        return genericErrorMessages.getOrDefault(locale, genericErrorMessages.get(Locale.ENGLISH));
    }
}
