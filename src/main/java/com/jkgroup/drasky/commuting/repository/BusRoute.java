package com.jkgroup.drasky.commuting.repository;

import com.jkgroup.drasky.intent.repository.Location;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"from", "to"})
@Entity
public class BusRoute {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String lineNumber;
    private String direction;
    private String stopName;

    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
    private Location from;

    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
    private Location to;
}
