package com.project.Plateforme.core.repository;

import com.project.Plateforme.core.bo.annotateur;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.Plateforme.core.bo.Annotation;
import com.project.Plateforme.core.bo.TextPair;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface annotationRepository extends JpaRepository<Annotation, Long> {
 public Optional<Annotation> findByAnnotateurAndTextPair(annotateur Annotateur, TextPair textPair);
 @Query("SELECT a.classeChoisie FROM Annotation a WHERE a.textPair.id = :tp")
 public Optional<String> findLabelByTextPairById(@Param("tp") Long tp);
 @Query("SELECT t FROM Annotation t WHERE t.textPair.dataset.id = :id")
 public List<Annotation> getAllByDatasetId(Long id);
 @Query("SELECT a.annotateur FROM Annotation a WHERE a.textPair = :textPair")
 List<annotateur> findAnnotateursByTextPair(@Param("textPair") TextPair textPair);
 @Query("SELECT a FROM Annotation a WHERE a.textPair.dataset.id = :datasetId AND a.annotateur.id = :annotateurId")
 List<Annotation> findByDatasetIdAndAnnotateurId(@Param("datasetId") long datasetId, @Param("annotateurId") long annotateurId);

}
