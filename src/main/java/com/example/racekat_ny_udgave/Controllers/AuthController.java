package com.example.racekat_ny_udgave.Controllers;

import com.example.racekat_ny_udgave.Model.User;
import com.example.racekat_ny_udgave.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        User user = userService.findUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            // Gem bruger i session
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("username", user.getUsername());
            return "redirect:/user/profile";
        } else {
            model.addAttribute("error", "Forkert email eller adgangskode");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("email") String email,
                           @RequestParam("ownerName") String ownerName,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           Model model) {

        // Check om brugeren allerede eksisterer
        if (userService.isEmailInUse(email)) {
            model.addAttribute("error", "Email er allerede i brug");
            return "register";
        }

        // Registrér ny bruger
        try {
            int userId = userService.generateUserId();//Overveje om vi skal smide det i servicelaget istedet
            userService.registerUser(email, userId, ownerName, username, password);
            model.addAttribute("success", "Din konto er blevet oprettet. Log venligst ind.");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", "Der opstod en fejl: " + e.getMessage());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}