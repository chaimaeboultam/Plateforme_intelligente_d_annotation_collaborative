package com.project.Plateforme.service;

import com.project.Plateforme.core.bo.TextPair;
import com.project.Plateforme.core.repository.annotationRepository;
import com.project.Plateforme.core.repository.textPairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.Plateforme.service.annotationService;
import com.project.Plateforme.core.bo.Annotation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class annotationServiceImpl implements annotationService {

    private final annotationRepository annotationRepository; // Correct the name here
    private final com.project.Plateforme.core.repository.textPairRepository textPairRepository;

    @Autowired
    public annotationServiceImpl(annotationRepository repository, textPairRepository textPairRepository) {
        this.annotationRepository = repository; // Corrected variable name
        this.textPairRepository = textPairRepository;
    }

    @Override
    public Optional<String> findLabelByTextPairById(Long tp) {
        return annotationRepository.findLabelByTextPairById(tp); // Corrected method call
    }
    @Override
    public float Getavancement(long id) {
        List<Annotation> annotationList = annotationRepository.getAllByDatasetId(id);
        List<TextPair> textPairList = textPairRepository.getAllByDatasetId(id);
        int AnnotationSize = annotationList.size();
        int TextPairSize = textPairList.size();
        float avancement = (AnnotationSize / (float) TextPairSize)*100;
        return avancement;

    }
}
