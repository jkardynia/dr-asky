package com.jkgroup.drasky.health;

import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.model.ParameterType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@AllArgsConstructor
@Getter
public enum Parameters {
    LOCATION("location", ParameterType.SYS_ANY);

    private String name;
    private ParameterType type;

    public static String getLocation(@RequestBody DialogFlowRequest request) {
        return Parameters.LOCATION.get(request)
                .filter(it -> it instanceof String)
                .map(it -> String.class.cast(it) )
                .orElse(""); // should never happen as it is required param
    }

    private Optional<Object> get(DialogFlowRequest request){
        return Optional.ofNullable(request.getQueryResult().getParameters().get(this.name));
    }
}
