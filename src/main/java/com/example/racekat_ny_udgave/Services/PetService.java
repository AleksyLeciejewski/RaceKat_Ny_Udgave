package com.example.racekat_ny_udgave.Services;

import com.example.racekat_ny_udgave.Infrastructure.Repositories.PetRepo;
import com.example.racekat_ny_udgave.Infrastructure.Repositories.UserRepo;
import com.example.racekat_ny_udgave.Model.Pet;
import com.example.racekat_ny_udgave.Model.User;

import java.util.List;

public class PetService {

    private UserService userService;
    private PetRepo petRepo;
    public PetService(PetRepo petRepo) {
        this.petRepo = petRepo;
    }

    public Pet registerPet(Pet pet){
        petRepo.addPet(pet);
        return pet;
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

    public List<Pet> getAllPets(){
        return petRepo.getAllPets();
    }

    public Pet getPetById(int id){
        return petRepo.findPetById(id);
    }

    public Pet findPetByOwner(int userId) {
        User user = userService.getUserById(userId);
        return petRepo.findPetByOwner(user);
    }




}
