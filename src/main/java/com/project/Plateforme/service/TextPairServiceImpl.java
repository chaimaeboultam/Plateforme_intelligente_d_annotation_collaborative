package com.project.Plateforme.service;
import com.project.Plateforme.core.bo.TextPair;
import com.project.Plateforme.core.bo.dataset;
import com.project.Plateforme.core.repository.textPairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextPairServiceImpl implements TextPairService {
    private final textPairRepository TextPairrepository;
    @Autowired
    public TextPairServiceImpl(textPairRepository TextPairrepository) {
        this.TextPairrepository = TextPairrepository;
    }
    @Override
    public void saveTextPair(TextPair textPair){
        TextPairrepository.save(textPair);
    }
    @Override
    public List<TextPair> findByDataset(dataset dataset){
        return TextPairrepository.findByDataset(dataset);
    }
}
