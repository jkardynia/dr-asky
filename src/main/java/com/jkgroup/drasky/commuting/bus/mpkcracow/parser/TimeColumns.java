package com.jkgroup.drasky.commuting.bus.mpkcracow.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class TimeColumns {
    private Map<Column, List<LocalTime>> columns;

    public List<LocalTime> get(Column column){
        return columns.get(column);
    }
}
