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
import com.jkgroup.drasky.intent.model.parameter.type.SysAny;
import com.jkgroup.drasky.intent.model.parameter.type.SysDate;
import com.jkgroup.drasky.intent.model.parameter.type.SysDuration;
import com.jkgroup.drasky.intent.model.parameter.type.SysTime;
import com.jkgroup.drasky.intent.repository.Location;
import com.jkgroup.drasky.intent.repository.Profile;
import com.jkgroup.drasky.intent.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.context.Context;

import java.time.*;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static com.jkgroup.drasky.intent.CalendarUtil.getLocalDateTimeFromNowOrDefault;
import static com.jkgroup.drasky.commuting.intent.FindBusAction.Parameters.*;

@IntentAction
public class FindBusAction implements Action {

    private final static String NAME = "find_bus";

    private BusLocationsRepository busLocationsRepository;
    private ProfileRepository profileRepository;
    private BusCheckingService busCheckingService;
    private TemplateGenerator templateGenerator;
    private Clock clock;
    private String defaultProfile;

    @Autowired
    public FindBusAction(BusLocationsRepository busLocationsRepository,
                         ProfileRepository profileRepository,
                         BusCheckingService busCheckingService,
                         TemplateGenerator templateGenerator,
                         Clock clock,
                         @Value("${dr-asky.default-profile}") String defaultProfile){
        this.busLocationsRepository = busLocationsRepository;
        this.profileRepository = profileRepository;
        this.busCheckingService = busCheckingService;
        this.templateGenerator = templateGenerator;
        this.clock = clock;
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
        return destination(request.getQueryResult().getParameters())
                .orElseThrow(() -> IntentException.mandatoryParameterIsMissing(DESTINATION_PARAMETER));
    }

    private LocalDateTime getRequestedDateTime(DialogFlowRequest request, String timeZone) {
        return getLocalDateTimeFromNowOrDefault(
                duration(request.getQueryResult().getParameters()).orElse(null),
                date(request.getQueryResult().getParameters()).orElse(null),
                time(request.getQueryResult().getParameters()).orElse(null),
                clock
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

    public static class Parameters {
        public static final String DESTINATION_PARAMETER = "destination";
        public static final String DURATION_PARAMETER = "duration";
        public static final String DATE_PARAMETER = "date";
        public static final String TIME_PARAMETER = "time";

        public static Optional<String> destination(Map<String, Object> params){
            return new SysAny().getValue(DESTINATION_PARAMETER, params);
        }

        public static Optional<Duration> duration(Map<String, Object> params){
            return new SysDuration().getValue(DURATION_PARAMETER, params);
        }

        public static Optional<LocalDate> date(Map<String, Object> params){
            return new SysDate().getValue(DATE_PARAMETER, params);
        }

        public static Optional<LocalTime> time(Map<String, Object> params){
            return new SysTime().getValue(TIME_PARAMETER, params);
        }
    }
}
