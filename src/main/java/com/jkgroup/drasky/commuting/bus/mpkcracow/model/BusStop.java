package com.jkgroup.drasky.commuting.bus.mpkcracow.model;

import lombok.Value;

@Value
public class BusStop {
    public static final BusStop DEFAULT = new BusStop("1", "default");

    String id;
    String name;
}
