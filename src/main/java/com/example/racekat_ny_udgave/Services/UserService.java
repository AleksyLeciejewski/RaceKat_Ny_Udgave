package com.example.racekat_ny_udgave.Services;

import com.example.racekat_ny_udgave.Infrastructure.Repositories.ProfileRepo;
import com.example.racekat_ny_udgave.Infrastructure.Repositories.UserRepo;
import com.example.racekat_ny_udgave.Model.Profile;
import com.example.racekat_ny_udgave.Model.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepo userRepo;
    private ProfileService profileService;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    public void setProfileService(@Lazy ProfileService profileService) {
        this.profileService = profileService;
    }


    public User registerUser(String email, String username, String password) {
        User user = new User(email, username, password);
        return userRepo.addUser(user);
    }

    public void updateUser(User user){
        userRepo.updateUser(user);
    }

    public User getUserById(int userId){
        return userRepo.getUser(userId);
    }

    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

    public void deleteUserById(int userId) {
        User user = getUserById(userId);
        if (user != null) {
            userRepo.deleteUser(user);
        }
    }

    public User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    public boolean isEmailInUse(String email) {
        return userRepo.findUserByEmail(email) != null;
    }


    public User getUserByProfileId(int profileId) {
        Profile profile = profileService.getProfileById(profileId);
        if (profile == null) return null;
        return getUserById(profile.getUserId());
    }
}