package com.project.Plateforme.core.bo;

import jakarta.persistence.*;

@Entity
public class classePossibles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nomClasse;
    public classePossibles() {}
    public classePossibles(String nomClasse) {
        this.nomClasse = nomClasse;
    }
    @ManyToOne
    @JoinColumn(name = "dataset_id")
    private dataset dataset;
    public Long getId() { return id; }
    public String getNomClasse() { return nomClasse; }
    public void setNomClasse(String nomClasse) { this.nomClasse = nomClasse; }
    public dataset getDataset() { return dataset; }
    public void setDataset(dataset dataset) { this.dataset = dataset; }

    @Override
    public String toString() {
        return nomClasse;
    }
}
