package com.project.Plateforme.service;

import com.project.Plateforme.core.bo.dataset;
import com.project.Plateforme.core.repository.datasetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class datasetServiceImpl implements datasetService {
    private final datasetRepository datasetRepository;
    @Autowired
    public datasetServiceImpl(datasetRepository datasetRepository) {
        this.datasetRepository = datasetRepository;
    }
    @Override
    public void saveDataset(dataset dataset){
        datasetRepository.save(dataset);
    }

    @Override
    public List<dataset> getAllDatasets(){
        return datasetRepository.findAll();
    }
}
