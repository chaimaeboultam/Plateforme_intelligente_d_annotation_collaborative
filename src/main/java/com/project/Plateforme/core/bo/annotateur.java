package com.project.Plateforme.core.bo;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("ANNOTATEUR")
public class annotateur extends User{

    @OneToMany(mappedBy = "annotateur")
    private List<Annotation> annotations;
    @OneToMany(mappedBy = "annotateur" , fetch = FetchType.EAGER)
    private List<tache> Tache;
    @Column(name = "actif")
    private boolean actif = true;
    public annotateur() {}
    public void setActif(boolean actif) {
        this.actif = actif;
    }
    public boolean getActif() {
        return actif;
    }
    public void setTache(List<tache> Tache) {
        this.Tache = Tache;
    }
    public List<tache> getTache() {
        return Tache;
    }
    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }
    public List<Annotation> getAnnotations() {
        return annotations;
    }

}
