package com.jkgroup.drasky.intent.repository;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    private String country;

    private String city;

    private String street;

    private String houseNumber;

    private String flatNumber;

    private String postCode;

    private String lat;

    private String lng;

    public String getAddress(){
        return street + " " + houseNumber + (flatNumber !=null ? "/" + flatNumber : "") + " " + city;
    }
}
