package com.SimSpot.ecommerce.dao;


import com.SimSpot.ecommerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * This is the Repository for the Order entity
 * We extend the JpaRepository to expose the underlying methods
 */

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     *  This method returns a page of Order entities that corresponds
     *  to the given Email. It uses pagination to abstract more Orders and
     *  uses pagination to allow the user to load by page size e.g 20 per page.
     *
     * @param email
     * @param pageable
     * @return
     */
    Page<Order> findByCustomerEmail(@Param("email") String email, Pageable pageable);
}
