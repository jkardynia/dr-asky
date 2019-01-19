package com.jkgroup.drasky.intent.model.parameter.type;

import com.jkgroup.drasky.intent.AppConfiguration;
import com.jkgroup.drasky.intent.model.parameter.DatePeriod;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

@Getter
public class SysDatePeriod{
    public Optional<DatePeriod> getValue(String name, Map<String, Object> params){

        return Optional.ofNullable(params.get(name))
                .filter(it -> it instanceof Map)
                .map(it -> (Map<String, String>) it)
                .filter(it -> it.containsKey("endDate") && it.containsKey("startDate"))
                .map(it -> DatePeriod.of(ZonedDateTime.parse(it.get("startDate"), AppConfiguration.DATE_TIME_FORMATTER),
                        ZonedDateTime.parse(it.get("endDate"), AppConfiguration.DATE_TIME_FORMATTER)));
    }
}
