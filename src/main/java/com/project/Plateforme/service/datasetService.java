package com.project.Plateforme.service;


import com.project.Plateforme.core.bo.dataset;

import java.util.List;

public interface datasetService {
    public void saveDataset(dataset dataset);
    public List<dataset> getAllDatasets();
}
