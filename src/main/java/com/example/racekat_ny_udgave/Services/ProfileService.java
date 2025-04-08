package com.example.racekat_ny_udgave.Services;

import com.example.racekat_ny_udgave.Infrastructure.Repositories.ProfileRepo;
import com.example.racekat_ny_udgave.Model.Profile;
import com.example.racekat_ny_udgave.Model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    private final ProfileRepo profileRepo;
    private final UserService userService;

    public ProfileService(ProfileRepo profileRepo, UserService userService) {
        this.profileRepo = profileRepo;
        this.userService = userService;
    }

    public Profile getProfileByUserId(int userId) {
        return profileRepo.getProfileByUserId(userId);
    }

    public Profile getProfileById(int profileId) {
        return profileRepo.getProfileById(profileId);
    }

    public Profile createProfile(int userId, String profileName, String description) {
        // Tjek om brugeren eksisterer
        User user = userService.getUserById(userId);
        if (user == null) {
            return null;
        }

        // Tjek om brugeren allerede har en profil
        Profile existingProfile = getProfileByUserId(userId);
        if (existingProfile != null) {
            return existingProfile; // Eller kast en exception
        }

        Profile newProfile = new Profile(0, 0, profileName, description);
        return profileRepo.createProfile(newProfile, userId);
    }

    public void updateProfile(Profile profile) {
        profileRepo.updateProfile(profile);
    }

    public void deleteProfile(int profileId) {
        profileRepo.deleteProfile(profileId);
    }

    public List<Profile> getAllProfiles() {
        return profileRepo.getAllProfiles();
    }
}