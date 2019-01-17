package com.jkgroup.drasky.commuting.bus;

import com.jkgroup.drasky.commuting.repository.BusRouteRepository;
import com.jkgroup.drasky.intent.repository.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BusCheckingService {

    private BusCheckingCracowService cracow;

    private BusRouteRepository busRouteRepository;

    @Autowired
    public BusCheckingService(BusCheckingCracowService cracow, BusRouteRepository busRouteRepository){
        this.cracow = cracow;
        this.busRouteRepository = busRouteRepository;
    }

    public BusInfo findBus(Location startingLocation, Location destination, LocalDateTime afterDate){
        return cracow.findBus(busRouteRepository.findByFromAndTo(startingLocation, destination), afterDate);
    }
}
