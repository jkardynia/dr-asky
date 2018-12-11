package com.jkgroup.drasky.commuting.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class OutputContextDto {
    private String name;
    private Integer lifespanCount;
    private Map<String, String> parameters;
}
