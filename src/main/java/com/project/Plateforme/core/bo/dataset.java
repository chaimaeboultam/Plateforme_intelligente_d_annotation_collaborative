package com.project.Plateforme.core.bo;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class dataset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nomDataset;
    @Column(nullable = false)
    private String description;
    @Column
    private String classes;
    @Column
    private String filePath;
    public dataset() {}
    public dataset(String nomDataset, String description, String classes) {
        this.nomDataset = nomDataset;
        this.description = description;
        this.classes = classes;
    }
    @OneToMany(mappedBy = "dataset", fetch = FetchType.EAGER)
    private List<TextPair> textPairs;
    @OneToMany(mappedBy = "dataset", fetch = FetchType.EAGER)
    private List<tache> taches;
    @OneToMany(mappedBy = "dataset")
    private List<classePossibles> classePossiblesList;
    public Long getId() { return id; }
    public String getNomDataset() { return nomDataset; }
    public String getDescription() { return description; }
    public void setId(Long id) { this.id = id; }
    public void setNomDataset(String nomDataset) { this.nomDataset = nomDataset; }
    public void setDescription(String description) { this.description = description; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public int getAvancement() {
        if (taches == null || taches.isEmpty()) {
            return 0; // pas de taches, donc 0%
        }
        long nbTachesTerminees = taches.stream().filter(t -> t.isTermine()).count();
        return (int) ((nbTachesTerminees * 100) / taches.size());
    }
    public List<TextPair> getTextPairs() { return textPairs; }
    public void setTextPairs(List<TextPair> textPairs) { this.textPairs = textPairs; }
    public void setClasses(String classes) { this.classes = classes; }
    public String getClasses() { return classes; }
    public void setClassePossiblesList (List<classePossibles> classePossiblesList) {
        this.classePossiblesList = classePossiblesList;
    }
    public List<classePossibles> getClassePossiblesList() { return classePossiblesList; }
    public void setTaches (List<tache> taches) { this.taches = taches; }
    public List<tache> getTaches() { return taches; }
}
