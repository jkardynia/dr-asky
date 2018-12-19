package com.jkgroup.drasky.intent.repository;

import com.jkgroup.drasky.commuting.entity.Destination;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String username;

    private String homeLocation;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "profile_id")
    private List<Destination> destinations = new ArrayList<>();

    public void addDestination(Destination entity) {
        destinations.add(entity);
        entity.setProfile(this);
    }

    public void removeDestination(Destination entity) {
        destinations.remove(entity);
        entity.setProfile(null);
    }
}
