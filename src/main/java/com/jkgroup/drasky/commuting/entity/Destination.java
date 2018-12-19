package com.jkgroup.drasky.commuting.entity;

import com.jkgroup.drasky.intent.repository.Profile;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Destination {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String alias;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    private Profile profile;
}