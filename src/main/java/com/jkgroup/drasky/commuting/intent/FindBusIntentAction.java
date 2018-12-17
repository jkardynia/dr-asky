package com.jkgroup.drasky.commuting.intent;

import com.jkgroup.drasky.commuting.Parameters;
import com.jkgroup.drasky.commuting.repository.DestinationRepository;
import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.dto.DialogFlowResponse;
import com.jkgroup.drasky.intent.model.IntentAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindBusIntentAction implements IntentAction {

    private DestinationRepository destinationRepository;

    @Autowired
    public FindBusIntentAction(DestinationRepository destinationRepository){
        this.destinationRepository = destinationRepository;
    }

    @Override
    public String getName() {
        return "Find bus";
    }

    @Override
    public DialogFlowResponse execute(DialogFlowRequest request) {
        String destination = Parameters.getDestination(request);
        String date = Parameters.getDate(request);
        String time = Parameters.getTime(request);

        return DialogFlowResponse
                .builder()
                .fulfillmentText("Looking for " + destination + " - " + getAddress(destination) + " for " + date + " " + time )
                .build();
    }



    private String getAddress(String alias){
        return destinationRepository.findOneByAlias(alias.toLowerCase())
                .map(it -> it.getAddress())
                .orElse(alias);
    }
}
