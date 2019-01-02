package com.jkgroup.drasky.intent.model.parameter.type;

import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import lombok.Getter;

import java.util.Optional;

@Getter
public class SysAny implements ParameterType<String>{
    private Class<String> type = String.class;

    public Optional<String> getValue(DialogFlowRequest request, String name){

        return getFromRequest(request, name)
                .filter(it -> it instanceof String)
                .map(it -> String.class.cast(it));
    }

    private Optional<Object> getFromRequest(DialogFlowRequest request, String name){
        return Optional.ofNullable(request.getQueryResult().getParameters().get(name));
    }
}
