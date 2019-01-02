package com.jkgroup.drasky.intent.model.parameter.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor(staticName = "of")
public class Duration {
    private Integer amount;
    private String unit;

    public Optional<java.time.Duration> toJavaDuration(){
        switch (unit){
            case "min": return Optional.of(java.time.Duration.ofMinutes(amount));
            case "h": return Optional.of(java.time.Duration.ofHours(amount));
            case "s": return Optional.of(java.time.Duration.ofSeconds(amount));
            case "day": return Optional.of(java.time.Duration.ofDays(amount));
            default: return Optional.empty();
        }
    }
}
