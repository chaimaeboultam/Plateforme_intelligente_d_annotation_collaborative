package com.project.Plateforme.service;
import com.project.Plateforme.core.bo.User;
import org.apache.tomcat.websocket.AuthenticationException;

public interface UserService {
    public User login(String login, String password);
    public String  genererMotDePasseAleatoire(int nbr);
}
