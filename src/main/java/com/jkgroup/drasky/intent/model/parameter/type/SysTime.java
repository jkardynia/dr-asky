package com.jkgroup.drasky.intent.model.parameter.type;

import com.jkgroup.drasky.intent.AppConfiguration;
import lombok.Getter;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

@Getter
public class SysTime {
    public Optional<LocalTime> getValue(String name, Map<String, Object> params){

        return Optional.ofNullable(params.get(name))
                .filter(it -> it instanceof String)
                .map(it -> String.class.cast(it) )
                .filter(it -> !it.isEmpty())
                .map(it -> ZonedDateTime.parse(it, AppConfiguration.DATE_TIME_FORMATTER))
                .map(it -> it.toLocalTime()); // DialogFLowApi will send date with timezone of currently logged in user (which should be same as Profile timezone) so timezone can be skipped for now
    }
}
