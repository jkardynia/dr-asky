package com.jkgroup.drasky.intent.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class OutputContextDto {
    private String name;
    private Map<String, Object> parameters;
}
