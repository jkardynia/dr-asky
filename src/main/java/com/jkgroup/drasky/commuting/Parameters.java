package com.jkgroup.drasky.commuting;

import com.jkgroup.drasky.intent.model.parameter.type.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Parameters {
    DESTINATION("destination", SysAny.class),
    DURATION("duration", SysDuration.class),
    DATE("date", SysDate.class),
    TIME("time", SysTime.class);

    private String name;
    private Class<? extends ParameterType<?>> type;
}
