package com.jkgroup.drasky.intent.model.parameter.type;

import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import lombok.Getter;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Getter
public class SysDuration implements ParameterType<Duration>{
    private Class<Duration> type = Duration.class;

    public Duration getValue(DialogFlowRequest request, String name){

        return getFromRequest(request, name)
                .filter(it -> it instanceof Map)
                .map(it -> (Map<String, Object>) it)
                .map(it -> com.jkgroup.drasky.intent.model.Duration.of(castToNumber(it.get("amount")), it.get("unit").toString()))
                .map(it -> it.toJavaDuration())
                .orElse(Duration.ZERO);
    }

    private Optional<Object> getFromRequest(DialogFlowRequest request, String name){
        return Optional.ofNullable(request.getQueryResult().getParameters().get(name));
    }

    private static Integer castToNumber(Object number) {
        return number instanceof Double ? ((Double) number).intValue() : (Integer) number;
    }
}
