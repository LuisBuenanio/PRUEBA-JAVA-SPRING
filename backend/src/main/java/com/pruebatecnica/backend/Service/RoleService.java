package com.pruebatecnica.backend.Service;

import com.pruebatecnica.backend.Entity.Role;
import com.pruebatecnica.backend.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role buscarPorNombre(String name) {
        return roleRepository.findByName(name);
    }

    // Otros métodos según necesidades
}