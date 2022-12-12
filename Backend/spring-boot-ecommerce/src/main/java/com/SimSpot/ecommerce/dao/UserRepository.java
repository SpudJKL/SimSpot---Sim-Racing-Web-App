package com.SimSpot.ecommerce.dao;

import java.util.Optional;

import com.SimSpot.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This is the Repository for the User entity
 * We extend the JpaRepository to expose the underlying methods
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * This method returns a optional object of User entities that corresponds
     * to the given username.
     * @param username
     * @return
     */
    Optional<User> findByUsername(String username);

    /**
     * This method returns a true of false if there is a corresponding user
     * @param email
     * @return
     */

    Boolean existsByEmail(String email);
}