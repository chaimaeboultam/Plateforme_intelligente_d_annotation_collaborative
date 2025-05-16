package com.project.Plateforme.core.repository;

import com.project.Plateforme.core.bo.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface tacheRepository extends JpaRepository<tache, Integer> {
    @Query("SELECT DISTINCT t.annotateur FROM tache t WHERE t.dataset.id = :datasetId")
    public List<annotateur> findAnnotateursByDatasetId(@Param("datasetId") Long id);
    public List<tache> findByDatasetId(Long id);
    @Query("SELECT T.annotateur FROM tache T WHERE T.id = :tacheId")
    public annotateur getAnnotateurByTacheId(@Param("tacheId") Integer id);
}
