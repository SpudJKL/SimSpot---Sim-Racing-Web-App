package com.SimSpot.ecommerce.dao;

import com.SimSpot.ecommerce.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * This is the Repository for the Product entity
 * We extend the JpaRepository to expose the underlying methods
 */

@RepositoryRestResource(collectionResourceRel = "productCategory", path = "category")
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
