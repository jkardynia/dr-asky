package com.jkgroup.drasky.health.service.airly;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Index {
    private String name;
    private Double value;
    private String level;
    private String description;
    private String advice;
    private String color;
}
