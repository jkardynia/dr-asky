package com.jkgroup.drasky.commuting.bus;

import com.jkgroup.drasky.commuting.bus.mpkcracow.BusCheckingCracowService;
import com.jkgroup.drasky.commuting.repository.BusRouteRepository;
import com.jkgroup.drasky.intent.model.ExternalServiceException;
import com.jkgroup.drasky.intent.repository.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BusCheckingService {

    private Map<String, BusCheckingCracowService> cityBusServices;

    private BusRouteRepository busRouteRepository;

    @Autowired
    public BusCheckingService(BusCheckingCracowService cracow, BusRouteRepository busRouteRepository){
        this.cityBusServices = new HashMap<>();
        this.cityBusServices.put("cracow", cracow);
        this.cityBusServices.put("kraków", cracow);
        this.cityBusServices.put("krakow", cracow);
        this.busRouteRepository = busRouteRepository;
    }

    public BusInfo findBus(Location startingLocation, Location destination, LocalDateTime afterDate){

        return Optional.ofNullable(cityBusServices.get(startingLocation.getCity().toLowerCase()))
                .map(service -> service.findBus(busRouteRepository.findByFromAndTo(startingLocation, destination), afterDate))
                .orElseThrow(() -> ExternalServiceException.notSupported(startingLocation.getCity()));
    }
}
