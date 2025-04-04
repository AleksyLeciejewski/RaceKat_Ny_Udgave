package com.example.racekat_ny_udgave.Controllers;

import com.example.racekat_ny_udgave.Model.Pet;
import com.example.racekat_ny_udgave.Model.User;
import com.example.racekat_ny_udgave.Services.PetService;
import com.example.racekat_ny_udgave.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PetService petService;

    @Autowired
    public UserController(UserService userService, PetService petService) {
        this.userService = userService;
        this.petService = petService;
    }

    // Beskyttet metode for at sikre, at brugeren er logget ind
    private User getAuthenticatedUser(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return null;
        }
        return userService.getUserById(userId);
    }

    @GetMapping("/profile")
    public String showUserProfile(HttpSession session, Model model) {
        User user = getAuthenticatedUser(session);
        if (user == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("user", user);

        // Hent brugerens kæledyr
        List<Pet> userPets = petService.getPetsByOwnerId(user.getUserId());
        model.addAttribute("pets", userPets);

        return "user/profile";
    }

    @GetMapping("/edit")
    public String showEditProfileForm(HttpSession session, Model model) {
        User user = getAuthenticatedUser(session);
        if (user == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("user", user);
        return "user/edit";
    }

    @PostMapping("/edit")
    public String updateProfile(@RequestParam("ownerName") String ownerName,
                                @RequestParam("username") String username,
                                @RequestParam("email") String email,
                                HttpSession session, Model model) {

        User user = getAuthenticatedUser(session);
        if (user == null) {
            return "redirect:/auth/login";
        }

        user.setOwnerName(ownerName);
        user.setUsername(username);
        user.setEmail(email);

        userService.updateUser(user);

        model.addAttribute("success", "Din profil er blevet opdateret");
        model.addAttribute("user", user);

        return "redirect:/user/profile";
    }

    @GetMapping("/delete")
    public String showDeleteConfirmation(HttpSession session, Model model) {
        User user = getAuthenticatedUser(session);
        if (user == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("user", user);
        return "user/delete-confirmation";
    }

    @PostMapping("/delete")
    public String deleteAccount(HttpSession session) {
        User user = getAuthenticatedUser(session);
        if (user == null) {
            return "redirect:/auth/login";
        }

        // Slet alle brugerens kæledyr først (vigtigt for database integritet)
        List<Pet> userPets = petService.getPetsByOwnerId(user.getUserId());
        for (Pet pet : userPets) {
            petService.deletePet(pet.getPetId());
        }

        // Slet brugeren
        userService.deleteUserById(user.getUserId());

        // Afslut session
        session.invalidate();

        return "redirect:/auth/login?deleted=true";
    }

    // Admin funktionalitet - kun tilgængelig for admin brugere
    @GetMapping("/list")
    public String listAllUsers(HttpSession session, Model model) {
        User user = getAuthenticatedUser(session);
        if (user == null) {
            return "redirect:/auth/login";
        }

        // I en rigtig app ville du kontrollere brugerens rolle her

        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("users", allUsers);

        return "user/list";
    }
}