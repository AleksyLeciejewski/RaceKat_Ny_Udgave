package com.example.racekat_ny_udgave.Model;

public class Pet {

    private int petId;
    private String petName;
    private int petAge;
    private String breed;
    private int profileId;

    public Pet(){}

    public Pet(int petId, String petName, int petAge, String breed, int ProfileId) {
        this.petId = petId;
        this.petName = petName;
        this.petAge = petAge;
        this.breed = breed;
        this.profileId = ProfileId;
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

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }
}
