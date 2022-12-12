package com.SimSpot.ecommerce.dao;

import java.util.Optional;

import com.SimSpot.ecommerce.entity.ERole;
import com.SimSpot.ecommerce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This is the Repository for the Role entity
 * We extend the JpaRepository to expose the underlying methods
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * This method returns an optional Role object that corresponds
     * to the given name.
     * @param name
     * @return
     */
    Optional<Role> findByName(ERole name);
}