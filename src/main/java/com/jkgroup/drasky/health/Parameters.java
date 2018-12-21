package com.jkgroup.drasky.health;

import com.jkgroup.drasky.intent.model.parameter.type.ParameterType;
import com.jkgroup.drasky.intent.model.parameter.type.SysAny;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Parameters {
    LOCATION("location", SysAny.class);

    private String name;
    private Class<? extends ParameterType<?>> type;
}
