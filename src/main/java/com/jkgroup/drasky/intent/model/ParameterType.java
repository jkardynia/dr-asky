package com.jkgroup.drasky.intent.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ParameterType {
    SYS_ANY(String.class),
    SYS_DURATION(Duration.class),
    SYS_DATE(String.class), // format 	2018-12-18T12:00:00+01:00
    SYS_TIME(String.class); // format 	2018-12-18T12:00:00+01:00

    private Class<?> type;
}
