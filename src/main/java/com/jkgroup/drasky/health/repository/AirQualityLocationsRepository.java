package com.jkgroup.drasky.health.repository;

import com.jkgroup.drasky.commuting.repository.IntentFindBus;
import com.jkgroup.drasky.intent.repository.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface AirQualityLocationsRepository extends PagingAndSortingRepository<IntentFindBus, Integer> {

    @Query("SELECT l FROM IntentAirQuality i JOIN i.profile p JOIN i.locations l " +
            "WHERE p.username = :username AND l.name = :name")
    Optional<Location> findOneByAliasForProfile(@Param("username") String username, @Param("name") String name);
}
