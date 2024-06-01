package com.fithub.repository.address;

import com.fithub.model.address.CountryState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "state", path = "state")
public interface CountryStateRepository extends JpaRepository<CountryState, Long> {
}
