package com.jkgroup.drasky.intent.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Duration {
    private Integer amount;
    private String unit;

    public java.time.Duration toJavaDuration(){
        switch (unit){
            case "min": return java.time.Duration.ofMinutes(amount);
            case "hour": return java.time.Duration.ofHours(amount);
            case "second": return java.time.Duration.ofSeconds(amount);
            case "day": return java.time.Duration.ofDays(amount);
            default: return java.time.Duration.ZERO;
        }
    }
}
