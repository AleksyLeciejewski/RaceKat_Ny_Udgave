package com.example.racekat_ny_udgave.Infrastructure.Repositories;

import com.example.racekat_ny_udgave.Model.Profile;

import java.util.List;

public interface ProfileRepoInt {
    Profile getProfileByUserId(int userId);
    Profile getProfileById(int profileId);
    Profile createProfile(Profile profile, int userId);
    void updateProfile(Profile profile);
    void deleteProfile(int profileId);
    List<Profile> getAllProfiles();
}