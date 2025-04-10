package com.example.racekat_ny_udgave.Controllers;

import com.example.racekat_ny_udgave.Model.User;
import com.example.racekat_ny_udgave.Services.ProfileService;
import com.example.racekat_ny_udgave.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AuthController {

    private final UserService userService;
    private final ProfileService profileService;

    @Autowired
    public AuthController(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
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
            session.setAttribute("user", user);
            return "redirect:/user/profile";
        } else {
            model.addAttribute("error", "Forkert email eller adgangskode");
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("email") String email,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           Model model) {

        // Check om brugeren allerede eksisterer
        if (userService.isEmailInUse(email)) {
            model.addAttribute("error", "Email er allerede i brug");
            return "redirect:/login";
        }

        // Registrér ny bruger
        try {
            userService.registerUser(email, username, password);
            model.addAttribute("success", "Din konto er blevet oprettet. Log venligst ind.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Der opstod en fejl: " + e.getMessage());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

//    private User getUserFromSession(HttpSession session) {
    //      return (User) session.getAttribute("user");
// }
    }

