package com.jkgroup.drasky.intent.model.parameter.type;

import com.jkgroup.drasky.intent.dto.DialogFlowRequest;

import java.util.Optional;

public interface ParameterType<T>{
     Class<T> getType();

     Optional<T> getValue(DialogFlowRequest request, String name);
}
