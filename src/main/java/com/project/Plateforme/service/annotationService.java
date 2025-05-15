package com.project.Plateforme.service;
import com.project.Plateforme.core.bo.TextPair;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface annotationService {
    public Optional<String> findLabelByTextPairById(Long tp);
    public float Getavancement( long datasetId);
}
