package com.project.Plateforme.core.bo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private Date dateLimite;

    @Column(nullable = false)
    private boolean termine = false;
    public tache(Date dateLimite) {
        this.dateLimite = dateLimite;
    }

    public tache() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getDateLimite() {
        return dateLimite;
    }
    public void setDateLimite(Date dateLimite) {
        this.dateLimite = dateLimite;
    }
    public boolean isTermine() {
        return termine;
    }
    public void setTermine(boolean termine) {
        this.termine = termine;
    }

    @OneToMany(mappedBy = "tache", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<TextPair> textPairs;
    @ManyToOne
    @JoinColumn(name = "annotateur_id")
    private annotateur annotateur;
    @ManyToOne
    @JoinColumn(name = "dataset_id")
    private dataset dataset;

    public annotateur getAnnotateur() {
        return annotateur;
    }
    public void setAnnotateur(annotateur annotateur) {
        this.annotateur = annotateur;
    }
    public dataset getDataset() {
    return dataset;}
    public void setDataset(dataset dataset) {
        this.dataset = dataset;
    }

    public List<TextPair> getTextPairs() {
        return textPairs;
    }
    public void setTextPairs(List<TextPair> textPairs) {
        this.textPairs = textPairs;
    }
}
