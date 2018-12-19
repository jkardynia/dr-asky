package com.jkgroup.drasky.commuting.intent;

import com.jkgroup.drasky.commuting.Parameters;
import com.jkgroup.drasky.commuting.bus.BusCheckingService;
import com.jkgroup.drasky.commuting.bus.BusInfo;
import com.jkgroup.drasky.commuting.repository.DestinationRepository;
import com.jkgroup.drasky.intent.IntentAction;
import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.dto.DialogFlowResponse;
import com.jkgroup.drasky.intent.model.Action;
import com.jkgroup.drasky.intent.model.IntentException;
import com.jkgroup.drasky.intent.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@IntentAction
public class FindBusIntentAction implements Action {

    private DestinationRepository destinationRepository;
    private ProfileRepository profileRepository;
    private BusCheckingService busCheckingService;
    private String name;
    private String defaultProfile;

    @Autowired
    public FindBusIntentAction(DestinationRepository destinationRepository,
                               ProfileRepository profileRepository,
                               BusCheckingService busCheckingService,
                               @Value("${dr-asky.intent.find-bus.action-name}") String actionName,
                               @Value("${dr-asky.default-profile}") String defaultProfile){
        this.destinationRepository = destinationRepository;
        this.profileRepository = profileRepository;
        this.busCheckingService = busCheckingService;
        this.name = actionName;
        this.defaultProfile = defaultProfile;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DialogFlowResponse execute(DialogFlowRequest request) {
        LocalDateTime dateTime = getRequestedDateTime(request);
        String destination = Parameters.getDestination(request);
        BusInfo busInfo = busCheckingService.findBus(getProfileHomeLocation(), getAddress(destination), dateTime);

        return DialogFlowResponse
                .builder()
                .fulfillmentText(getFulfillmentText(destination, busInfo))
                .build();
    }

    private LocalDateTime getRequestedDateTime(DialogFlowRequest request) {
        Duration duration = Parameters.getDuration(request);

        if(!Duration.ZERO.equals(duration)){
            return LocalDateTime.now().plusSeconds(duration.getSeconds());
        }

        return LocalDateTime.of(Parameters.getDate(request), Parameters.getTime(request));
    }

    private String getProfileHomeLocation() {
        return profileRepository.findOneByUsername(defaultProfile)
                .map(it -> it.getHomeLocation())
                .orElseThrow(() -> IntentException.profileHomeLocationNotSet(defaultProfile));
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
        return destinationRepository.findOneByAliasForProfile(defaultProfile, alias.toLowerCase())
                .map(it -> it.getAddress())
                .orElse(alias);
    }
}
