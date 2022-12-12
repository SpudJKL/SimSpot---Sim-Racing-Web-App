package com.SimSpot.ecommerce.dao;

import com.SimSpot.ecommerce.entity.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;


/**
 * This is the Repository for the County entity
 * We extend the JpaRepository to expose the underlying methods
 */

@RepositoryRestResource(collectionResourceRel = "counties", path = "counties")

public interface CountyRepository extends JpaRepository<County, Integer> {

    /**
     * This method returns a list with County entities that corresponds
     * to the given Country code.
     * @param code
     * @return
     */
    List<County> findByCountryCode(@Param("code") String code);
}