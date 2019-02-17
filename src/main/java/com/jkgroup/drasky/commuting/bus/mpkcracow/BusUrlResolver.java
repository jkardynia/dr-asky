package com.jkgroup.drasky.commuting.bus.mpkcracow;

import com.jkgroup.drasky.commuting.bus.mpkcracow.model.BusStop;
import com.jkgroup.drasky.commuting.bus.mpkcracow.model.Direction;

import java.net.MalformedURLException;

public interface BusUrlResolver {
    String resolveUrl(String busNumber, Direction direction, BusStop busStop) throws MalformedURLException;
}
