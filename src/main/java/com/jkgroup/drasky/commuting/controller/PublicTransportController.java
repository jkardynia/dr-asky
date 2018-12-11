package com.jkgroup.drasky.commuting.controller;

import com.jkgroup.drasky.commuting.dto.DialogFlowRequest;
import com.jkgroup.drasky.commuting.dto.DialogFlowResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("commuting/public-transport")
public class PublicTransportController {

    @PostMapping("/intent/when-bus-arrives")
    public Object whenBusArrives(@RequestBody DialogFlowRequest request) {
        return DialogFlowResponse
                .builder()
                .fulfillmentText("Hello world! You asked for " + request.getQueryResult().getParameters().get("destination").get(0))
                .build();
    }
}
