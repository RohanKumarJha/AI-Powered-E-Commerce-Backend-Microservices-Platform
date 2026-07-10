package com.ecommerce.repository;

import com.ecommerce.model.ENUM.RoleType;
import com.ecommerce.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    boolean existsByRoleType(RoleType roleType);
}
