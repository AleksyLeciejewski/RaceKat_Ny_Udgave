package com.example.racekat_ny_udgave.Services;

import com.example.racekat_ny_udgave.Infrastructure.Repositories.PetRepo;
import com.example.racekat_ny_udgave.Model.Pet;
import com.example.racekat_ny_udgave.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {

    private PetRepo petRepo;

    public PetService(PetRepo petRepo) {
        this.petRepo = petRepo;
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

    public List<Pet> getAllPets(){
        return petRepo.getAllPets();
    }

    public Pet getPetById(int id){
        return petRepo.findPetById(id);
    }

    public List<Pet> getPetsByOwnerId(int ownerId) {
        List<Pet> allPets = getAllPets();
        List<Pet> ownerPets = new ArrayList<>();

        for (Pet pet : allPets) {
            if (pet.getPetOwner(pet) != null && pet.getPetOwner(pet).getUserId() == ownerId) {
                ownerPets.add(pet);
            }
        }

        return ownerPets;
    }
}