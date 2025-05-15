package com.project.Plateforme.service;

import com.project.Plateforme.core.bo.TextPair;
import com.project.Plateforme.core.bo.dataset;

import java.util.List;

public interface TextPairService {
    //public List<TextPair> getAllTextPairs();
    public void saveTextPair(TextPair textPair);
    public List<TextPair> findByDataset(dataset dataset);
}
