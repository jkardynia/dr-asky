package com.jkgroup.drasky.commuting.repository;

import com.jkgroup.drasky.commuting.entity.Destination;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface DestinationRepository extends PagingAndSortingRepository<Destination, Integer> {

    @Query("SELECT d FROM Profile p JOIN Destination d ON p.id = d.profile WHERE p.username = :username AND d.alias = :alias")
    Optional<Destination> findOneByAliasForProfile(@Param("username") String username, @Param("alias") String alias);
}
