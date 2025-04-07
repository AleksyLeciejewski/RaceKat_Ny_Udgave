package com.example.racekat_ny_udgave.Model;

public class Profile {

    private int profileId;
    private String profileName;
    private String profileDescription;
    public Profile(int profileId, String profileName, String profileDescription) {
        this.profileId = profileId;
        this.profileName = profileName;
        this.profileDescription = profileDescription;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }
}
