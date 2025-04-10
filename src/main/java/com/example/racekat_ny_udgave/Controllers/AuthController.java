package com.example.racekat_ny_udgave.Controllers;

import com.example.racekat_ny_udgave.Model.Profile;
import com.example.racekat_ny_udgave.Model.User;
import com.example.racekat_ny_udgave.Services.PetService;
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
    private final PetService petService;

    @Autowired
    public AuthController(UserService userService, ProfileService profileService, PetService petService) {
        this.userService = userService;
        this.profileService = profileService;
        this.petService = petService;
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
            // Tjek om brugeren er admin
            if ("admin".equalsIgnoreCase(user.getRole())) {
                return "redirect:/admin/dashboard";
            }
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



        try {
            // Registrér ny bruger
            User newUser = userService.registerUser(email, username, password);
            newUser.setRole("USER");

            // Opret en standardprofil for denne bruger
            Profile defaultProfile = new Profile(0, newUser.getUsername(), "Standard profil", newUser.getUserId());
            Profile oprettetProfile = profileService.createProfile(newUser.getUserId(), defaultProfile.getProfileName(), defaultProfile.getProfileDescription());
            System.out.println("Oprettet profil med id: " + oprettetProfile.getProfileId());

            model.addAttribute("success", "Din konto er blevet oprettet. Log venligst ind.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Der opstod en fejl: " + e.getMessage());
            System.out.println(e.getMessage());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String showAdminPage() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }
    @GetMapping("/admin/pets")
    public String showPets(Model model) {
        model.addAttribute("pets", petService.getAllPets());
        return "admin/pets";
    }
}


