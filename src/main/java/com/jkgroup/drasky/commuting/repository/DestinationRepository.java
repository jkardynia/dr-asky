package com.jkgroup.drasky.commuting.repository;

import com.jkgroup.drasky.commuting.entity.Destination;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DestinationRepository extends CrudRepository<Destination, Integer> {
    Optional<Destination> findOneByAlias(String alias);
}
