package com.SimSpot.ecommerce.dao;

import com.SimSpot.ecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This is the Repository for the Customer entity
 * We extend the JpaRepository to expose the underlying methods
 */

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    /**
     * This method returns a Customer entity that corresponds
     * to the given Email.
     * @param theEmail
     * @return
     */

    Customer findByEmail(String theEmail);


}
