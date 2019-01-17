package com.jkgroup.drasky.intent.repository;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String username;

    @OneToOne(cascade=CascadeType.PERSIST)
    private Location homeLocation;

    private String timezone = "UTC";
}
