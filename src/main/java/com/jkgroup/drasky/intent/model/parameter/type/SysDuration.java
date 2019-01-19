package com.jkgroup.drasky.intent.model.parameter.type;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

public class SysDuration {
    public Optional<Duration> getValue(String name, Map<String, Object> params){

        return Optional.ofNullable(params.get(name))
                .filter(it -> it instanceof Map)
                .map(it -> (Map<String, Object>) it)
                .filter(it -> it.containsKey("amount") && it.containsKey("unit"))
                .filter(it -> it.get("amount") instanceof Number)
                .map(it -> com.jkgroup.drasky.intent.model.parameter.Duration.of(Number.class.cast(it.get("amount")).intValue(), it.get("unit").toString()))
                .flatMap(it -> it.toJavaDuration());
    }
}
