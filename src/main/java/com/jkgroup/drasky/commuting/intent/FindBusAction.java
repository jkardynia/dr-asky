package com.jkgroup.drasky.commuting.intent;

import com.jkgroup.drasky.commuting.bus.BusCheckingService;
import com.jkgroup.drasky.commuting.bus.BusInfo;
import com.jkgroup.drasky.commuting.repository.BusLocationsRepository;
import com.jkgroup.drasky.intent.IntentAction;
import com.jkgroup.drasky.intent.TemplateGenerator;
import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.dto.DialogFlowResponse;
import com.jkgroup.drasky.intent.model.Action;
import com.jkgroup.drasky.intent.model.IntentClientException;
import com.jkgroup.drasky.intent.model.IntentException;
import com.jkgroup.drasky.intent.model.parameter.ParameterResolver;
import com.jkgroup.drasky.intent.model.parameter.type.*;
import com.jkgroup.drasky.intent.repository.Location;
import com.jkgroup.drasky.intent.repository.Profile;
import com.jkgroup.drasky.intent.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

import static com.jkgroup.drasky.intent.CalendarUtil.getLocalDateTimeFromNowOrDefault;

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

        Profile profile = getDefaultProfile();
        LocalDateTime dateTime = getRequestedDateTime(request, profile.getTimezone());
        String destination = getDestination(request);

        BusInfo busInfo = busCheckingService.findBus(profile.getHomeLocation(), getLocation(destination), dateTime);

        return DialogFlowResponse
                .builder()
                .fulfillmentText(getFulfillmentText(destination, busInfo))
                .build();
    }

    private String getDestination(DialogFlowRequest request) {
        return ParameterResolver.getSysAnyValue(request, Parameters.DESTINATION.getName())
                .orElseThrow(() -> IntentException.mandatoryParameterIsMissing(Parameters.DESTINATION.getName()));
    }

    private LocalDateTime getRequestedDateTime(DialogFlowRequest request, String timeZone) {
        return getLocalDateTimeFromNowOrDefault(
                ParameterResolver.getSysDurationValue(request, Parameters.DURATION.getName()).orElse(null),
                ParameterResolver.getSysDateValue(request, Parameters.DATE.getName()).orElse(null),
                ParameterResolver.getSysTimeValue(request, Parameters.TIME.getName()).orElse(null)
        )
        .withZoneSameInstant(ZoneId.of(timeZone))
        .toLocalDateTime();
    }

    private Profile getDefaultProfile() {
        return profileRepository.findOneByUsername(defaultProfile)
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

    private Location getLocation(String alias){
        return busLocationsRepository.findOneByAliasForProfile(defaultProfile, alias.toLowerCase())
                .orElseThrow(() -> IntentClientException.locationNotSet(alias));
    }

    private boolean isToday(LocalDateTime date){
        return LocalDate.now().equals(date.toLocalDate());
    }

    @AllArgsConstructor
    @Getter
    public enum Parameters {
        DESTINATION("destination", SysAny.class),
        DURATION("duration", SysDuration.class),
        DATE("date", SysDate.class),
        TIME("time", SysTime.class);

        private String name;
        private Class<? extends ParameterType<?>> type;
    }
}
