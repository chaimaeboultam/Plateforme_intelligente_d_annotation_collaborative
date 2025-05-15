package com.project.Plateforme.service;
import com.project.Plateforme.core.bo.annotateur;
import com.project.Plateforme.core.repository.annotateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class annotateurServiceImpl implements annotateurService {
    private final annotateurRepository annotateurRepository;
    @Autowired
    public annotateurServiceImpl(annotateurRepository annotateurRepository) {
        this.annotateurRepository = annotateurRepository;
    }
    @Override
    public List<annotateur> getAllAnnotateur(){
        return annotateurRepository.findAll();
    }

}
