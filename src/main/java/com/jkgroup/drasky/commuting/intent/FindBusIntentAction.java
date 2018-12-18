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
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FindBusIntentAction implements IntentAction {

    private DestinationRepository destinationRepository;
    private BusCheckingService busCheckingService;
    private String name;

    @Autowired
    public FindBusIntentAction(DestinationRepository destinationRepository,
                               BusCheckingService busCheckingService,
                               @Value("${dr-asky.intent.find-bus.action-name}") String actionName){
        this.destinationRepository = destinationRepository;
        this.busCheckingService = busCheckingService;
        this.name = actionName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DialogFlowResponse execute(DialogFlowRequest request) {
        String destination = Parameters.getDestination(request);
        String myLocation = "ul Pachonskiego 14";

        LocalDateTime dateTime = LocalDateTime.of(Parameters.getDate(request), Parameters.getTime(request));

        BusInfo busInfo = busCheckingService.findBus(myLocation, getAddress(destination), dateTime);

        return DialogFlowResponse
                .builder()
                .fulfillmentText(getFulfillmentText(destination, busInfo))
                .build();
    }

    private String getFulfillmentText(String destination, BusInfo busInfo) {
        final ClassPathResource resource = new ClassPathResource("ssml-templates/find-bus.ssml");

        String template = "";
        try {
            template = new String(Files.readAllBytes(Paths.get(resource.getFile().getPath())));
        } catch (IOException e) {
            // ignore
        }

        return template.replace("{{destination}}", destination)
            .replace("{{bus_number}}", busInfo.getBusNumber())
            .replace("{{date}}", busInfo.getArrivalTime().format(DateTimeFormatter.ISO_LOCAL_DATE))
            .replace("{{time}}", busInfo.getArrivalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    private String getAddress(String alias){
        return destinationRepository.findOneByAlias(alias.toLowerCase())
                .map(it -> it.getAddress())
                .orElse(alias);
    }
}
