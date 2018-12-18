package com.jkgroup.drasky.commuting.bus;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Service
public class BusCheckingService {
    public BusInfo findBus(String startingLocation, String destination, LocalDateTime time){
        return new BusInfo("144", ZonedDateTime.now());
    }
}
