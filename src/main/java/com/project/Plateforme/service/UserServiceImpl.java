package com.project.Plateforme.service;

import com.project.Plateforme.core.bo.User;
import com.project.Plateforme.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.Math;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String login, String password) {
        Optional<User> optionalUser = userRepository.findByLogin(login);

        if (optionalUser.isEmpty()) {
            System.out.println("Login failed: User not found with login = " + login);
            throw new IllegalArgumentException("Utilisateur non trouvé.");  // French: User not found
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(password)) {
            System.out.println("Login failed: Incorrect password for user = " + login);
            throw new IllegalArgumentException("Mot de passe incorrect.");  // French: Incorrect password
        }

        System.out.println("Login successful for user: " + login);
        return user;
    }
    public String genererMotDePasseAleatoire(int longueur) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder motDePasse = new StringBuilder();
        for (int i = 0; i < longueur; i++) {
            int index = (int) (Math.random() * caracteres.length());
            motDePasse.append(caracteres.charAt(index));
        }
        return motDePasse.toString();
    }
}
