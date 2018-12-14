package com.jkgroup.drasky.commuting.repository;

import com.jkgroup.drasky.commuting.entity.Destination;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface DestinationRepository extends PagingAndSortingRepository<Destination, Integer> {
    Optional<Destination> findOneByAlias(String alias);
}
