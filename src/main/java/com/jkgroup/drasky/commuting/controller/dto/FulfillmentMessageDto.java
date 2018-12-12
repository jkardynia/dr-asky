package com.jkgroup.drasky.commuting.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FulfillmentMessageDto {
    private TextMessagesListDto text;
}

@Getter
class TextMessagesListDto {
    private List<String> text;
}
