package com.pruebatecnica.backend.Repository;

import com.pruebatecnica.backend.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}