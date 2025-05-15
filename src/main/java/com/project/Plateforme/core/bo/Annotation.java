package com.project.Plateforme.core.bo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;
@Entity
public class Annotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String classeChoisie;
    // Lien avec le TextPair
    @ManyToOne
    @JoinColumn(name = "text_pair_id", nullable = false)
    private TextPair textPair;

    // Lien avec l'utilisateur qui a fait l'annotation
    @ManyToOne
    @JoinColumn(name = "annotateur_id", nullable = false)
    private annotateur annotateur;



    // Constructeurs
    public Annotation() {}

    public Annotation(TextPair textPair, annotateur user) {
        this.textPair = textPair;
        this.annotateur = user;
    }

    public String getClasseChoisie() {
        return classeChoisie;
    }
    public void setClasseChoisie(String classeChoisie) {
        this.classeChoisie = classeChoisie;
    }
    // Getters et Setters
    public Long getId() { return id; }

    public TextPair getTextPair() { return textPair; }
    public void setTextPair(TextPair textPair) { this.textPair = textPair; }

    public annotateur getUser() { return annotateur; }
    public void setUser(annotateur user) { this.annotateur = user; }
    public annotateur getAnnotateur() { return annotateur; }
    public void setAnnotateur(annotateur annotateur) {
        this.annotateur = annotateur;
    }




}

