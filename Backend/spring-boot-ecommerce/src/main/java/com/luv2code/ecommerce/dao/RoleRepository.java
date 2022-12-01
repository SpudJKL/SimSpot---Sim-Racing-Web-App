package com.luv2code.ecommerce.dao;

import java.util.Optional;

import com.luv2code.ecommerce.entity.ERole;
import com.luv2code.ecommerce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}