package com.project.Plateforme.web;

import com.project.Plateforme.core.bo.User;
import com.project.Plateforme.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class userController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showLoginForm() {
        return "login";  // returns the login.html view
    }

    @PostMapping
    public String login(
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        System.out.println("Login attempt with username: " + login);

        try {
            User user = userService.login(login, password);

            if (user.getRole() != null) {
                String roleName = user.getRole().getNomRole();
                System.out.println("User role: " + roleName);

                if ("ADMINISTRATEUR".equalsIgnoreCase(roleName)) {
                    return "redirect:/admin";
                } else if ("ANNOTATEUR".equalsIgnoreCase(roleName)) {
                    session.setAttribute("user", user);
                    return "redirect:/annotateur";
                }
            }

            model.addAttribute("error", "Rôle utilisateur inconnu.");  // Unknown role
            return "login";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());  // Show specific error (user not found or wrong password)
            return "login";
        }
    }
}
