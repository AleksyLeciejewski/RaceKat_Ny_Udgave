package com.example.racekat_ny_udgave.Controllers;

import com.example.racekat_ny_udgave.Model.Pet;
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

import java.util.List;

@Controller
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    private final UserService userService;
    private final ProfileService profileService;

    @Autowired
    public PetController(PetService petService, UserService userService, ProfileService profileService) {
        this.petService = petService;
        this.userService = userService;
        this.profileService = profileService;
    }

    //Tjekker om bruger fortsat er logget ind i sessionen
    private User getAuthenticatedUser(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return null;
        }
        return userService.getUserById(userId);
    }

 /*   @GetMapping("/list")
    public String listPets(HttpSession session, Model model) {
        User user = getAuthenticatedUser(session);
        if (user == null) {
            return "redirect:/login";
        }

        List<Pet> userPets = petService.getPetsByUserId(user.getUserId());
        model.addAttribute("pets", userPets);

        return "pet/list";
    }

  */

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

            Profile profile = profileService.getProfileByUserId(user.getUserId());
            if (profile == null) {
                model.addAttribute("Error", "Ingen profil fundet, SQL Problemer ;)");
                return "pet/create";
            }
            // Opret et nyt Pet objekt
            Pet pet = new Pet();
            pet.setPetName(petName);
            pet.setPetAge(petAge);
            pet.setBreed(breed);
            pet.setProfileId(profile.getProfileId());

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

        Profile profile = profileService.getProfileById(pet.getProfileId());
        if (pet == null || profile == null || profile.getUserId() != user.getUserId()) {
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
        Profile profile = profileService.getProfileById(pet.getProfileId());
        if (pet == null || profile == null || profile.getUserId() != user.getUserId()) {
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
        Profile profile = profileService.getProfileById(pet.getProfileId());
        if (pet == null || profile == null || profile.getUserId() != user.getUserId()) {
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
        // (pet == null || pet.getPetOwner(pet).getUserId() != user.getUserId())
        //Gammel kode bliver her lige, hvis det nye jeg har lavet ik virker.
        Profile profile = profileService.getProfileById(pet.getProfileId());
        if (pet == null || profile == null || profile.getUserId() != user.getUserId()) {
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

    @GetMapping("/dine katte")
    public String ShowUserCats(HttpSession session, Model model) {
        User user = getAuthenticatedUser(session);
        Profile profile = profileService.getProfileById(user.getUserId());
        if (profile != null) {
            List<Pet> pets = petService.getPetsByProfileId(profile.getProfileId());
            model.addAttribute("pets", pets);
        }
        return "profile";
    }

}