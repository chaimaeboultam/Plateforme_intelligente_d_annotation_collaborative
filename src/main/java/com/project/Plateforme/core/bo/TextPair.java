package com.project.Plateforme.core.bo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class TextPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text1;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text2;

    @OneToMany(mappedBy = "textPair", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Annotation> annotations;
    @ManyToOne
    @JoinColumn(name = "dataset_id")
    private dataset dataset;
    @ManyToOne
    @JoinColumn(name = "tache_id", nullable = false, unique = true)
    private tache tache;

    // Constructors
    public TextPair() {}

    public TextPair(String text1, String text2) {
        this.text1 = text1;
        this.text2 = text2;
    }


    // Getters and Setters
    public Long getId() { return id; }

    public String getText1() { return text1; }
    public void setText1(String text1) { this.text1 = text1; }

    public String getText2() { return text2; }
    public void setText2(String text2) { this.text2 = text2; }


    public List<Annotation> getAnnotations() { return annotations; }
    public void setAnnotations(List<Annotation> annotations) { this.annotations = annotations; }
    public dataset getDataset() { return dataset; }
    public void setDataset(dataset dataset) { this.dataset = dataset; }
    public void setTache(tache tache) { this.tache = tache; }
    public tache getTache() { return tache; }
}