package com.example.racekat_ny_udgave.Model;

public class Pet {

    private int petId;
    private String petName;
    private int petAge;
    private String breed;
    User owner;

    Pet(){}

    Pet(int petId, String petName, int petAge, String breed, User owner) {
        this.petId = petId;
        this.petName = petName;
        this.petAge = petAge;
        this.breed = breed;
        this.owner = owner;
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

    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }









}
