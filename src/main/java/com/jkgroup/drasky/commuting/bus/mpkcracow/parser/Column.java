package com.jkgroup.drasky.commuting.bus.mpkcracow.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Column{
    HOUR("Hour"), WORK_DAY("Workday"), SATURDAY("Saturdays"), HOLIDAY("Holidays");

    private String columnName;

    public static Column fromName(String col) {
        return Arrays.asList(values())
                .stream()
                .filter(it -> it.getColumnName().equals(col))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid column name " + col));
    }
}
