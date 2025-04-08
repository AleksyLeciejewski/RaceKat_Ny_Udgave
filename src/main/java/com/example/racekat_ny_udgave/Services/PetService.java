package com.example.racekat_ny_udgave.Services;

import com.example.racekat_ny_udgave.Infrastructure.Repositories.PetRepo;
import com.example.racekat_ny_udgave.Model.Pet;
import com.example.racekat_ny_udgave.Model.Profile;
import com.example.racekat_ny_udgave.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {

    private final ProfileService profileService;
    private PetRepo petRepo;

    public PetService(PetRepo petRepo, ProfileService profileService) {
        this.petRepo = petRepo;
        this.profileService = profileService;
    }

    public Pet registerPet(Pet pet){
        return petRepo.addPet(pet);
    }

    public Pet updatePet(Pet pet){
        petRepo.updatePet(pet);
        return pet;
    }

    public void deletePet(int id){
        Pet pet = petRepo.findPetById(id);
        if (pet != null) {
            petRepo.deletePet(pet);
        }
    }

    public boolean isOwnerOfPet(int userId, Pet pet) {
        Profile profile = profileService.getProfileById(pet.getProfileId());
        return profile != null && profile.getUserId() == userId;
    }

    public List<Pet> getAllPets(){
        return petRepo.getAllPets();
    }
    public Pet getPetById(int id){
        return petRepo.findPetById(id);
    }

    // Omdøb til getPetsByProfileId for klarhed
    public List<Pet> getPetsByProfileId(int profileId) {
        List<Pet> allPets = getAllPets();
        List<Pet> profilePets = new ArrayList<>();

        for (Pet pet : allPets) {
            if (pet.getProfileId() == profileId) {
                profilePets.add(pet);
            }
        }
        return profilePets;
    }

    // Ny metode for at få kæledyr via userId
    public List<Pet> getPetsByUserId(int userId) {
        Profile profile = profileService.getProfileByUserId(userId);
        if (profile == null) {
            return new ArrayList<>(); // Brugeren har ingen profil
        }
        return getPetsByProfileId(profile.getProfileId());
    }

    public boolean isOwnedByUser(int petId, int userId) {
        Pet pet = getPetById(petId);
        if (pet == null) return false;

        Profile profile = profileService.getProfileById(pet.getProfileId());
        return profile != null && profile.getUserId() == userId;
    }
}