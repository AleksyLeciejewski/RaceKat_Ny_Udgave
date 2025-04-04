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
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    private final UserService userService;

    @Autowired
    public PetController(PetService petService, UserService userService) {
        this.petService = petService;
        this.userService = userService;
    }

    //Tjekker om bruger fortsat er logget ind i sessionen
    private User getAuthenticatedUser(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return null;
        }
        return userService.getUserById(userId);
    }

    @GetMapping("/list")
    public String listPets(HttpSession session, Model model) {
        User user = getAuthenticatedUser(session);
        if (user == null) {
            return "redirect:/auth/login";
        }

        List<Pet> userPets = petService.getPetsByOwnerId(user.getUserId());
        model.addAttribute("pets", userPets);

        return "pet/list";
    }

    @GetMapping("/create")
    public String showCreatePetForm(HttpSession session) {
        User user = getAuthenticatedUser(session);
        if (user == null) {
            return "redirect:/auth/login";
        }

        return "pet/create";
    }

    @PostMapping("/create")
    public String createPet(
        @RequestParam("petName") String petName,
        @RequestParam("petAge") int petAge,
        @RequestParam("breed") String breed,
        HttpSession session, Model model) {

        User user = getAuthenticatedUser(session);
        if (user == null) {
            return "redirect:/auth/login";
        }

        try {
            // Opret et nyt Pet objekt
            Pet pet = new Pet();
            pet.setPetName(petName);
            pet.setPetAge(petAge);
            pet.setBreed(breed);

            //Gemmer userID for nu, senere kan vi lavere en løsere kobling i koden, og sætte forholdet op gennem database keys
            pet.setOwnerId(user.getUserId());

            petService.registerPet(pet);

            return "redirect:/pet/list";
        } catch (Exception e) {
            model.addAttribute("error", "Der opstod en fejl: " + e.getMessage());
            return "pet/create";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditPetForm(@PathVariable("id") int petId, HttpSession session, Model model) {
        User user = getAuthenticatedUser(session);
        if (user == null) {
            return "redirect:/auth/login";
        }

        Pet pet = petService.getPetById(petId);

        // Sikkerhedscheck, sikrer kun ejeren kan redigere sit kæledyr
        if (pet == null || pet.getPetOwner(pet).getUserId() != user.getUserId()) {
            return "redirect:/pet/list";
        }

        model.addAttribute("pet", pet);
        return "pet/edit";
    }

    @PostMapping("/edit/{id}")
    public String updatePet(@PathVariable("id") int petId, @RequestParam("petName") String petName, @RequestParam("petAge") int petAge, @RequestParam("breed") String breed, HttpSession session, Model model) {
        User user = getAuthenticatedUser(session);
        if (user == null) {
            return "redirect:/auth/login";
        }
        Pet pet = petService.getPetById(petId);
        if (pet == null || pet.getPetOwner(pet).getUserId() != user.getUserId()) {
            return "redirect:/pet/list";
        }

        pet.setPetName(petName);
        pet.setPetAge(petAge);
        pet.setBreed(breed);

        petService.updatePet(pet);

        return "redirect:/pet/list";
    }

    @GetMapping("/delete/{id}")
    public String showDeletePetConfirmation(@PathVariable("id") int petId,
                                            HttpSession session, Model model) {
        User user = getAuthenticatedUser(session);
        if (user == null) {
            return "redirect:/auth/login";
        }
        Pet pet = petService.getPetById(petId);
        if (pet == null || pet.getPetOwner(pet).getUserId() != user.getUserId()) {
            return "redirect:/pet/list";
        }
        model.addAttribute("pet", pet);
        return "pet/delete-confirmation";
    }

    @PostMapping("/delete/{id}")
    public String deletePet(@PathVariable("id") int petId, HttpSession session) {
        User user = getAuthenticatedUser(session);
        if (user == null) {
            return "redirect:/auth/login";
        }
        Pet pet = petService.getPetById(petId);
        if (pet == null || pet.getPetOwner(pet).getUserId() != user.getUserId()) {
            return "redirect:/pet/list";
        }
        petService.deletePet(petId);
        return "redirect:/pet/list";
    }

    @GetMapping("/{id}")
    public String viewPetDetails(@PathVariable("id") int petId,
                                 HttpSession session, Model model) {
        User user = getAuthenticatedUser(session);
        if (user == null) {
            return "redirect:/auth/login";
        }
        Pet pet = petService.getPetById(petId);
        if (pet == null) {
            return "redirect:/pet/list";
        }

        model.addAttribute("pet", pet);
        return "pet/details";
    }

    // Admin funktionalitet - kun tilgængelig for admin brugere
    @GetMapping("/all")
    public String listAllPets(HttpSession session, Model model) {
        User user = getAuthenticatedUser(session);
        if (user == null) {
            return "redirect:/auth/login";
        }

        //Plads til eventuel logik for kontrol af brugerens rolle, tænker vi kan lave en admin og almindelig user template til hver

        List<Pet> allPets = petService.getAllPets();
        model.addAttribute("pets", allPets);
        return "pet/all";
    }
}