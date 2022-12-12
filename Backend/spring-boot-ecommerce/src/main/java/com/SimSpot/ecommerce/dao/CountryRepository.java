package com.SimSpot.ecommerce.dao;

import com.SimSpot.ecommerce.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * This is the Repository for the Country entity
 * We extend the JpaRepository to expose the underlying methods
 */
@RepositoryRestResource(collectionResourceRel = "countries", path = "countries")

public interface CountryRepository extends JpaRepository<Country, Integer> {

}
