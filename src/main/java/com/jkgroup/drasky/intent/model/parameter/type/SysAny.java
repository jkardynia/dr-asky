package com.jkgroup.drasky.intent.model.parameter.type;

import java.util.Map;
import java.util.Optional;

public class SysAny {
    public Optional<String> getValue(String name, Map<String, Object> params){

        return Optional.ofNullable(params.get(name))
                .filter(it -> it instanceof String)
                .map(it -> String.class.cast(it));
    }
}
