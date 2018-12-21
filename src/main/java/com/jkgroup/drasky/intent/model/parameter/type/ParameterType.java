package com.jkgroup.drasky.intent.model.parameter.type;

import com.jkgroup.drasky.intent.dto.DialogFlowRequest;

public interface ParameterType<T>{
     Class<T> getType();

     T getValue(DialogFlowRequest request, String name);
}
