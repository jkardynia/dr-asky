package com.jkgroup.drasky.commuting.repository;

import com.jkgroup.drasky.intent.repository.Location;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface BusRouteRepository extends PagingAndSortingRepository<BusRoute, Integer> {

    List<BusRoute> findByFromAndTo(Location from, Location to);
}
