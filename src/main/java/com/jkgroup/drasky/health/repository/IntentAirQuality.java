package com.jkgroup.drasky.health.repository;

import com.jkgroup.drasky.intent.repository.Location;
import com.jkgroup.drasky.intent.repository.Profile;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class IntentAirQuality {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
    private Profile profile;

    @OneToMany(cascade=CascadeType.PERSIST)
    private List<Location> locations = new ArrayList<>();
}