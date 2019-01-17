package com.jkgroup.drasky.fixtures


import com.jkgroup.drasky.intent.repository.Location
import com.jkgroup.drasky.intent.repository.Profile

class ProfileFactory {
    static Profile createProfileWithHomeLocation(String profileName, Location location) {
        def profile = new Profile()
        profile.setUsername(profileName)
        profile.setHomeLocation(location)
        profile.setTimezone("Europe/Warsaw")
        profile
    }

    static Location createSimpleLocation(String name, String city, String street, String houseNumber){
        def location = new Location()
        location.setName(name)
        location.setCity(city)
        location.setStreet(street)
        location.setHouseNumber(houseNumber)
        location.setLat("10.234")
        location.setLng("50.034")

        location
    }
}
