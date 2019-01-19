package com.jkgroup.drasky.intent.model.parameter.type;

import com.jkgroup.drasky.intent.AppConfiguration;
import lombok.Getter;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

@Getter
public class SysDate {
    public Optional<LocalDate> getValue(String name, Map<String, Object> params){

        return Optional.ofNullable(params.get(name))
                .filter(it -> it instanceof String)
                .map(it -> String.class.cast(it) )
                .filter(it -> !it.isEmpty())
                .map(it -> ZonedDateTime.parse(it, AppConfiguration.DATE_TIME_FORMATTER))
                .map(it -> it.toLocalDate());
    }
}
