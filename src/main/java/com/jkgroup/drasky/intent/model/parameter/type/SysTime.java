package com.jkgroup.drasky.intent.model.parameter.type;

import com.jkgroup.drasky.intent.AppConfiguration;
import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import lombok.Getter;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Optional;

@Getter
public class SysTime implements ParameterType<LocalTime>{
    private Class<LocalTime> type = LocalTime.class;

    public LocalTime getValue(DialogFlowRequest request, String name){

        return getFromRequest(request, name)
                .filter(it -> it instanceof String)
                .map(it -> String.class.cast(it) )
                .filter(it -> !it.isEmpty())
                .map(it -> ZonedDateTime.parse(it, AppConfiguration.DATE_TIME_FORMATTER))
                .map(it -> it.toLocalTime())
                .orElse(LocalTime.now());
    }

    private Optional<Object> getFromRequest(DialogFlowRequest request, String name){
        return Optional.ofNullable(request.getQueryResult().getParameters().get(name));
    }
}
