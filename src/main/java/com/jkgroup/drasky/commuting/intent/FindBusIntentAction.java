package com.jkgroup.drasky.commuting.intent;

import com.jkgroup.drasky.commuting.Parameters;
import com.jkgroup.drasky.commuting.bus.BusCheckingService;
import com.jkgroup.drasky.commuting.bus.BusInfo;
import com.jkgroup.drasky.commuting.repository.DestinationRepository;
import com.jkgroup.drasky.intent.IntentAction;
import com.jkgroup.drasky.intent.TemplateGenerator;
import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.dto.DialogFlowResponse;
import com.jkgroup.drasky.intent.model.Action;
import com.jkgroup.drasky.intent.model.IntentException;
import com.jkgroup.drasky.intent.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.context.Context;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@IntentAction
public class FindBusIntentAction implements Action {

    private DestinationRepository destinationRepository;
    private ProfileRepository profileRepository;
    private BusCheckingService busCheckingService;
    private TemplateGenerator templateGenerator;
    private String name;
    private String defaultProfile;

    @Autowired
    public FindBusIntentAction(DestinationRepository destinationRepository,
                               ProfileRepository profileRepository,
                               BusCheckingService busCheckingService,
                               TemplateGenerator templateGenerator,
                               @Value("${dr-asky.intent.find-bus.action-name}") String actionName,
                               @Value("${dr-asky.default-profile}") String defaultProfile){
        this.destinationRepository = destinationRepository;
        this.profileRepository = profileRepository;
        this.busCheckingService = busCheckingService;
        this.templateGenerator = templateGenerator;
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

        Context context = new Context();
        context.setVariable("destination", destination);
        context.setVariable("busInfo", busInfo);
        context.setVariable("date", isToday(busInfo.getArrivalTime()) ? null : busInfo.getArrivalTime());
        context.setVariable("time", busInfo.getArrivalTime());

        return templateGenerator.parse("ssml-templates/find-bus.ssml", context);
    }

    private String getAddress(String alias){
        return destinationRepository.findOneByAliasForProfile(defaultProfile, alias.toLowerCase())
                .map(it -> it.getAddress())
                .orElse(alias);
    }

    private boolean isToday(LocalDateTime date){
        return LocalDate.now().equals(date.toLocalDate());
    }
}
