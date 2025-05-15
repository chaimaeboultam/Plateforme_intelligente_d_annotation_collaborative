package com.project.Plateforme.service;
import com.project.Plateforme.core.repository.classePossiblesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class classePossiblesServiceImpl implements classePossiblesService {
    private final classePossiblesRepository classePossiblesRepository;
    @Autowired
    public classePossiblesServiceImpl(classePossiblesRepository classePossiblesRepository) {
        this.classePossiblesRepository = classePossiblesRepository;
    }
}
