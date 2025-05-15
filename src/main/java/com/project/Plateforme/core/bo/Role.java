package com.project.Plateforme.core.bo;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true,nullable = false)
    private String nomRole;
    @OneToMany(mappedBy = "role")
    private List<User> users;
    public Role() {
    }
    public Role(String nomRole) {
        this.nomRole = nomRole;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNomRole() {
        return nomRole;
    }

    public void setNomRole(String nomRole) {
    this.nomRole = nomRole;}
    }
