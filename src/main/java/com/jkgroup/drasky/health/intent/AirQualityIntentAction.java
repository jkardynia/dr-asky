package com.jkgroup.drasky.health.intent;

import com.jkgroup.drasky.health.Parameters;
import com.jkgroup.drasky.health.service.airly.AirQuality;
import com.jkgroup.drasky.health.service.airly.AirlyService;
import com.jkgroup.drasky.intent.IntentAction;
import com.jkgroup.drasky.intent.TemplateGenerator;
import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.dto.DialogFlowResponse;
import com.jkgroup.drasky.intent.model.Action;
import com.jkgroup.drasky.intent.model.parameter.ParameterResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.context.Context;

@IntentAction
public class AirQualityIntentAction implements Action {

    private AirlyService airlyService;
    private TemplateGenerator templateGenerator;
    private String name;
    private String defaultProfile;

    @Autowired
    public AirQualityIntentAction(AirlyService airlyService,
                                  TemplateGenerator templateGenerator,
                                  @Value("${dr-asky.intent.air-quality.action-name}") String actionName,
                                  @Value("${dr-asky.default-profile}") String defaultProfile){
        this.airlyService = airlyService;
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

        String location = ParameterResolver.getSysAnyValue(request, Parameters.LOCATION.getName());
        AirQuality airQuality = airlyService.checkAirQuality(location);

        return DialogFlowResponse
                .builder()
                .fulfillmentText(getFulfillmentText(location, airQuality))
                .build();
    }

    private String getFulfillmentText(String location, AirQuality airQuality) {

        Context context = new Context();
        context.setVariable("location", location);
        context.setVariable("currentAirQuality", airQuality.getCurrent());

        return templateGenerator.parse("ssml-templates/air-quality.ssml", context);
    }
}
