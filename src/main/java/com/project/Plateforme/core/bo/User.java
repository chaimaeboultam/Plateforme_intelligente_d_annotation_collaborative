package com.project.Plateforme.core.bo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import org.hibernate.usertype.UserType;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;



    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;
    public User() {}

    public User(String login, String password, com.project.Plateforme.core.bo.Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public com.project.Plateforme.core.bo.Role getRole() { return role; }

    public void setRole(com.project.Plateforme.core.bo.Role role) { this.role = role; }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
