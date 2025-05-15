package com.project.Plateforme.core.repository;

import com.project.Plateforme.core.bo.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface roleRepository extends JpaRepository<Role, Integer> {
    public Role findById(int id);
}
