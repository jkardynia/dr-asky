package com.jkgroup.drasky.commuting.bus;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class BusInfo {
    private String busNumber;
    private LocalDateTime arrivalTime;
}
