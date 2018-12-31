package com.jkgroup.drasky.health.intent;

import com.jkgroup.drasky.health.repository.AirQualityLocationsRepository;
import com.jkgroup.drasky.health.Parameters;
import com.jkgroup.drasky.health.service.airly.AirQuality;
import com.jkgroup.drasky.health.service.airly.AirlyService;
import com.jkgroup.drasky.intent.IntentAction;
import com.jkgroup.drasky.intent.TemplateGenerator;
import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.dto.DialogFlowResponse;
import com.jkgroup.drasky.intent.model.Action;
import com.jkgroup.drasky.intent.model.IntentException;
import com.jkgroup.drasky.intent.model.parameter.ParameterResolver;
import com.jkgroup.drasky.intent.repository.Location;
import com.jkgroup.drasky.intent.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.context.Context;

@IntentAction
public class AirQualityAction implements Action {
    private final static String NAME = "air_quality";

    private AirlyService airlyService;
    private TemplateGenerator templateGenerator;
    private ProfileRepository profileRepository;
    private AirQualityLocationsRepository airQualityLocationsRepository;
    private String defaultProfile;

    @Autowired
    public AirQualityAction(AirlyService airlyService,
                            TemplateGenerator templateGenerator,
                            ProfileRepository profileRepository,
                            AirQualityLocationsRepository airQualityLocationsRepository,
                            @Value("${dr-asky.default-profile}") String defaultProfile){
        this.airlyService = airlyService;
        this.templateGenerator = templateGenerator;
        this.profileRepository = profileRepository;
        this.airQualityLocationsRepository = airQualityLocationsRepository;
        this.defaultProfile = defaultProfile;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public DialogFlowResponse execute(DialogFlowRequest request) {
        String locationName = ParameterResolver.getSysAnyValue(request, Parameters.LOCATION.getName());
        Location location = getLocation(locationName);

        AirQuality airQuality = airlyService.checkAirQuality(location.getLat(), location.getLng());

        return DialogFlowResponse
                .builder()
                .fulfillmentText(getFulfillmentText(locationName, airQuality))
                .build();
    }

    private Location getLocation(String locationName) {

        if(locationName.isEmpty()){
            return getProfileHomeLocation();
        }

        return airQualityLocationsRepository.findOneByAliasForProfile(defaultProfile, locationName)
                .orElseThrow(() -> IntentException.locationNotSet(locationName));
    }

    private String getFulfillmentText(String locationName, AirQuality airQuality) {

        Context context = new Context();
        context.setVariable("locationName", locationName);
        context.setVariable("currentAirQuality", airQuality.getCurrent());

        return templateGenerator.parse("ssml-templates/air-quality.ssml", context);
    }

    private Location getProfileHomeLocation() {
        return profileRepository.findOneByUsername(defaultProfile)
                .map(it -> it.getHomeLocation())
                .orElseThrow(() -> IntentException.profileHomeLocationNotSet(defaultProfile));
    }
}
