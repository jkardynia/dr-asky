package com.jkgroup.drasky.intent.model.parameter;

import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.model.parameter.type.SysAny;
import com.jkgroup.drasky.intent.model.parameter.type.SysDate;
import com.jkgroup.drasky.intent.model.parameter.type.SysDuration;
import com.jkgroup.drasky.intent.model.parameter.type.SysTime;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class ParameterResolver {

    public static Optional<String> getSysAnyValue(DialogFlowRequest request, String name){
        SysAny type = new SysAny();

        return type.getValue(request, name);
    }

    public static Optional<Duration> getSysDurationValue(DialogFlowRequest request, String name){
        SysDuration type = new SysDuration();

        return type.getValue(request, name);
    }

    public static Optional<LocalDate> getSysDateValue(DialogFlowRequest request, String name){
        SysDate type = new SysDate();

        return type.getValue(request, name);
    }

    public static Optional<LocalTime> getSysTimeValue(DialogFlowRequest request, String name){
        SysTime type = new SysTime();

        return type.getValue(request, name);
    }
}
