package com.example.racekat_ny_udgave.Infrastructure.Repositories;

import com.example.racekat_ny_udgave.Model.Pet;
import com.example.racekat_ny_udgave.Model.User;

import java.util.List;

public interface PetRepoInt {

    List<Pet> getAllPets();
    Pet addPet(Pet pet);
    void deletePet(Pet pet);
    Pet findPetById(int id);
    void updatePet(Pet pet);
    Pet findPetByOwner(User user);

}
