package com.jkgroup.drasky.commuting.bus.mpkcracow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class TimeTable {
    private String lineNumber;
    private Direction direction;
    private BusStop stopName;
    private List<LocalTime> workday;
    private List<LocalTime> saturday;
    private List<LocalTime> holiday;
}
