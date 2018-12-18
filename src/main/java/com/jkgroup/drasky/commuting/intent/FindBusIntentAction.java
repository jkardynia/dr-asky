package com.jkgroup.drasky.commuting.intent;

import com.jkgroup.drasky.commuting.Parameters;
import com.jkgroup.drasky.commuting.bus.BusCheckingService;
import com.jkgroup.drasky.commuting.bus.BusInfo;
import com.jkgroup.drasky.commuting.repository.DestinationRepository;
import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.dto.DialogFlowResponse;
import com.jkgroup.drasky.intent.model.IntentAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FindBusIntentAction implements IntentAction {

    private DestinationRepository destinationRepository;
    private BusCheckingService busCheckingService;
    private String intentName;

    @Autowired
    public FindBusIntentAction(DestinationRepository destinationRepository,
                               BusCheckingService busCheckingService,
                               @Value("${dr-asky.intent.find-bus.name}") String intentName){
        this.destinationRepository = destinationRepository;
        this.busCheckingService = busCheckingService;
        this.intentName = intentName;
    }

    @Override
    public String getName() {
        return intentName;
    }

    @Override
    public DialogFlowResponse execute(DialogFlowRequest request) {
        String destination = Parameters.getDestination(request);
        String myLocation = "ul Pachonskiego 14";

        LocalDateTime dateTime = LocalDateTime.of(Parameters.getDate(request), Parameters.getTime(request));

        BusInfo busInfo = busCheckingService.findBus(myLocation, getAddress(destination), dateTime);

        return DialogFlowResponse
                .builder()
                .fulfillmentText("There will be bus number " + busInfo.getBusNumber() + " to " + destination + " at " + busInfo.getArrivalTime().toLocalTime().toString() )
                .build();
    }

    private String getAddress(String alias){
        return destinationRepository.findOneByAlias(alias.toLowerCase())
                .map(it -> it.getAddress())
                .orElse(alias);
    }
}
