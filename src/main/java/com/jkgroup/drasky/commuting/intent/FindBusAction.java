package com.jkgroup.drasky.commuting.intent;

import com.jkgroup.drasky.commuting.Parameters;
import com.jkgroup.drasky.commuting.bus.BusCheckingService;
import com.jkgroup.drasky.commuting.bus.BusInfo;
import com.jkgroup.drasky.commuting.repository.BusLocationsRepository;
import com.jkgroup.drasky.intent.IntentAction;
import com.jkgroup.drasky.intent.TemplateGenerator;
import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.dto.DialogFlowResponse;
import com.jkgroup.drasky.intent.model.Action;
import com.jkgroup.drasky.intent.model.IntentException;
import com.jkgroup.drasky.intent.model.parameter.ParameterResolver;
import com.jkgroup.drasky.intent.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;

@IntentAction
public class FindBusAction implements Action {

    private final static String NAME = "find_bus";

    private BusLocationsRepository busLocationsRepository;
    private ProfileRepository profileRepository;
    private BusCheckingService busCheckingService;
    private TemplateGenerator templateGenerator;
    private String defaultProfile;

    @Autowired
    public FindBusAction(BusLocationsRepository busLocationsRepository,
                         ProfileRepository profileRepository,
                         BusCheckingService busCheckingService,
                         TemplateGenerator templateGenerator,
                         @Value("${dr-asky.default-profile}") String defaultProfile){
        this.busLocationsRepository = busLocationsRepository;
        this.profileRepository = profileRepository;
        this.busCheckingService = busCheckingService;
        this.templateGenerator = templateGenerator;
        this.defaultProfile = defaultProfile;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public DialogFlowResponse execute(DialogFlowRequest request) {
        LocalDateTime dateTime = getRequestedDateTime(request);
        String destination = ParameterResolver.getSysAnyValue(request, Parameters.DESTINATION.getName())
                .orElseThrow(() -> IntentException.mandatoryParameterIsMissing(Parameters.DESTINATION.getName()));
        BusInfo busInfo = busCheckingService.findBus(getProfileHomeLocation(), getAddress(destination), dateTime);

        return DialogFlowResponse
                .builder()
                .fulfillmentText(getFulfillmentText(destination, busInfo))
                .build();
    }

    private LocalDateTime getRequestedDateTime(DialogFlowRequest request) {
        return ParameterResolver.getSysDurationValue(request, Parameters.DURATION.getName())
                .map(duration -> LocalDateTime.now().plusSeconds(duration.getSeconds()))
                .orElse(LocalDateTime.of(ParameterResolver.getSysDateValue(request, Parameters.DATE.getName()).orElse(LocalDate.now()),
                    ParameterResolver.getSysTimeValue(request, Parameters.TIME.getName()).orElse(LocalTime.now())));
    }

    private String getProfileHomeLocation() {
        return profileRepository.findOneByUsername(defaultProfile)
                .map(it -> it.getHomeLocation().getAddress())
                .orElseThrow(() -> IntentException.profileLocationNotSet(defaultProfile));
    }

    private String getFulfillmentText(String destination, BusInfo busInfo) {

        Context context = new Context(Locale.ENGLISH);
        context.setVariable("destination", destination);
        context.setVariable("busNumber", busInfo.getBusNumber());
        context.setVariable("date", isToday(busInfo.getArrivalTime()) ? null : busInfo.getArrivalTime());
        context.setVariable("time", busInfo.getArrivalTime());

        return templateGenerator.parse("ssml-templates/find-bus.ssml", context);
    }

    private String getAddress(String alias){
        return busLocationsRepository.findOneByAliasForProfile(defaultProfile, alias.toLowerCase())
                .map(it -> it.getAddress())
                .orElse(alias);
    }

    private boolean isToday(LocalDateTime date){
        return LocalDate.now().equals(date.toLocalDate());
    }
}
