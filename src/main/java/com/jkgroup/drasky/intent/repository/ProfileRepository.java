package com.jkgroup.drasky.intent.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface ProfileRepository extends PagingAndSortingRepository<Profile, Integer> {
    Optional<Profile> findOneByUsername(String username);
}
