package com.project.Plateforme.core.repository;

import com.project.Plateforme.core.bo.administrateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface adminRepository extends JpaRepository<administrateur, Long> {

}
