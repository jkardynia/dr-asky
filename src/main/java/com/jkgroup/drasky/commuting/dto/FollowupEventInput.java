package com.jkgroup.drasky.commuting.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class FollowupEventInput {
    private String name;
    private String languageCode;
    private Map<String, String> parameters;
}
