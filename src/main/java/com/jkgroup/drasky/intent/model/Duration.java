package com.jkgroup.drasky.intent.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class Duration {
    private Integer amount;
    private String unit;

    public java.time.Duration toJavaDuration(){
        switch (unit){
            case "min": return java.time.Duration.ofMinutes(amount);
            case "h": return java.time.Duration.ofHours(amount);
            case "s": return java.time.Duration.ofSeconds(amount);
            case "day": return java.time.Duration.ofDays(amount);
            default: return java.time.Duration.ZERO;
        }
    }
}
