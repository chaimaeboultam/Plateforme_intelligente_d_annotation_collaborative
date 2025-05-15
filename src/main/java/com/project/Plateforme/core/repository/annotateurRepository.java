package com.project.Plateforme.core.repository;

import com.project.Plateforme.core.bo.annotateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface annotateurRepository extends JpaRepository<annotateur, Long> {
    @Query("SELECT a FROM annotateur a WHERE a.id IN :ids")
    List<annotateur> findByIdIn(@Param("ids") List<Long> ids);
    annotateur findById(long id);
}
