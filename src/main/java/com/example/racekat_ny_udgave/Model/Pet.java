package com.example.racekat_ny_udgave.Model;

import com.example.racekat_ny_udgave.Services.UserService;

public class Pet {

    private UserService userService;

    private int petId;
    private String petName;
    private int petAge;
    private String breed;
    private int ownerId;

    public Pet(){}

    public Pet(int petId, String petName, int petAge, String breed, int ownerId) {
        this.petId = petId;
        this.petName = petName;
        this.petAge = petAge;
        this.breed = breed;
        this.ownerId = ownerId;
    }


    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getPetAge() {
        return petAge;
    }

    public void setPetAge(int petAge) {
        this.petAge = petAge;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public User getPetOwner(Pet pet) {
        return userService.getUserById(pet.getOwnerId());
    }
}
