package com.project.Plateforme.core.repository;
import com.project.Plateforme.core.bo.TextPair;
import com.project.Plateforme.core.bo.dataset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface textPairRepository extends JpaRepository<TextPair, Long>{
    public TextPair getTextPairById(Long id);
    public List<TextPair> getTextPairByTacheId(Integer tacheId);
    @Query("SELECT t FROM TextPair t WHERE t.dataset = :dataset")
    public List<TextPair> findByDataset(dataset dataset);
    @Query("SELECT t FROM TextPair t WHERE t.dataset.id = :id")
    public List<TextPair> getAllByDatasetId(@Param("id") long id);
}
