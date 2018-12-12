package com.jkgroup.drasky.commuting.controller;

import com.jkgroup.drasky.commuting.controller.dto.DialogFlowRequest;
import com.jkgroup.drasky.commuting.controller.dto.DialogFlowResponse;
import com.jkgroup.drasky.commuting.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("commuting/public-transport")
public class PublicTransportController {

    private DestinationRepository destinationRepository;

    @Autowired
    public PublicTransportController(DestinationRepository destinationRepository){
        this.destinationRepository = destinationRepository;
    }

    @PostMapping("/intent/when-bus-arrives")
    public DialogFlowResponse whenBusArrives(@RequestBody DialogFlowRequest request) {
        return DialogFlowResponse
                .builder()
                .fulfillmentText("Hello world! You asked for " + getAddress(getDestinationParameter(request)))
                .build();
    }

    private String getDestinationParameter(@RequestBody DialogFlowRequest request) {
        return request.getQueryResult().getParameters().get("destination").get(0);
    }

    private String getAddress(String alias){
        return destinationRepository.findOneByAlias(alias.toLowerCase())
                .map(it -> it.getAddress())
                .orElse(alias);
    }
}
