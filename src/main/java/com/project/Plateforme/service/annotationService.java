package com.project.Plateforme.service;
import com.project.Plateforme.core.bo.TextPair;
import com.project.Plateforme.core.bo.annotateur;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface annotationService {
    public Optional<String> findLabelByTextPairById(Long tp);
    public float Getavancement( long datasetId);
    public annotateur findAnnotateurByTextPair(TextPair textPair);
    public float GetAvancementAnnotateur(long datasetId, long annotateurId);
}
