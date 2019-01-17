package com.jkgroup.drasky.intent.model.parameter.type;

import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import lombok.Getter;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Getter
public class SysDuration implements ParameterType<Duration>{
    private Class<Duration> type = Duration.class;

    public Optional<Duration> getValue(DialogFlowRequest request, String name){

        return getFromRequest(request, name)
                .filter(it -> it instanceof Map)
                .map(it -> (Map<String, Object>) it)
                .filter(it -> it.containsKey("amount") && it.containsKey("unit"))
                .filter(it -> it.get("amount") instanceof Number)
                .map(it -> com.jkgroup.drasky.intent.model.parameter.type.Duration.of(Number.class.cast(it.get("amount")).intValue(), it.get("unit").toString()))
                .flatMap(it -> it.toJavaDuration());
    }

    private Optional<Object> getFromRequest(DialogFlowRequest request, String name){
        return Optional.ofNullable(request.getQueryResult().getParameters().get(name));
    }
}
