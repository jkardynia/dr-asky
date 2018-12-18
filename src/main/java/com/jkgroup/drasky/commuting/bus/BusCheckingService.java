package com.jkgroup.drasky.commuting.bus;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BusCheckingService {
    public BusInfo findBus(String startingLocation, String destination, LocalDateTime time){
        return new BusInfo("144", getRandomArrivalTime(time));
    }

    private LocalDateTime getRandomArrivalTime(LocalDateTime time) {
        return time.plusMinutes((long)Math.floor(Math.random()*50));
    }
}
