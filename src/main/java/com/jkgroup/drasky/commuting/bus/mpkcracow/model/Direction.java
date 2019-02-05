package com.jkgroup.drasky.commuting.bus.mpkcracow.model;

import lombok.Value;

@Value
public class Direction {
    public static final Direction DEFAULT = new Direction("1", "default");

    String id;
    String name;
}
