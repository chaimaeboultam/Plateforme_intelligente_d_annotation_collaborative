package com.project.Plateforme.service;

import com.project.Plateforme.core.bo.TextPair;
import com.project.Plateforme.core.bo.annotateur;
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
    @Override
    public float GetAvancementAnnotateur(long datasetId, long annotateurId) {
        // 1. Récupérer tous les TextPairs affectés à cet annotateur dans ce dataset
        List<TextPair> textPairsAffectes = textPairRepository.getAllByDatasetIdAndAnnotateurId(datasetId, annotateurId);

        // 2. Récupérer les annotations faites par cet annotateur dans ce dataset
        List<Annotation> annotations = annotationRepository.findByDatasetIdAndAnnotateurId(datasetId, annotateurId);

        int nbTextPairsAffectes = textPairsAffectes.size();
        int nbAnnotations = annotations.size();

        if (nbTextPairsAffectes == 0) return 0f;

        float avancement = (nbAnnotations / (float) nbTextPairsAffectes) * 100;
        return avancement;
    }

    public annotateur findAnnotateurByTextPair(TextPair textPair) {
        List<annotateur> annotateurs = annotationRepository.findAnnotateursByTextPair(textPair);
        return annotateurs.isEmpty() ? null : annotateurs.get(0);
    }
}
