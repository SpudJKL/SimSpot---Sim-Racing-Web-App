package com.SimSpot.ecommerce.dao;

import com.SimSpot.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

/**
 * This is the Repository for the Product entity
 * We extend the JpaRepository to expose the underlying methods
 */

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     *  This method returns a page object of Product entities that corresponds
     *  to the given Category ID. It uses pagination to abstract more products and
     *  uses pagination to allow the user to load by page size e.g 20 per page.
     *
     * @param id
     * @param pageable
     * @return
     */

    Page<Product> findByCategoryId(@Param("id") Long id, Pageable pageable);

    /**
     *  This method returns a list of Product entities that corresponds
     *  to the given name
     *
     * @param name
     * @return
     */
    List<Product> findByNameContaining(String name);
}
