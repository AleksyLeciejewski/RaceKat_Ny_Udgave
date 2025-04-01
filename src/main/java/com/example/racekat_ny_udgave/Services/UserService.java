package com.example.racekat_ny_udgave.Services;

import com.example.racekat_ny_udgave.Infrastructure.Repositories.UserRepo;
import com.example.racekat_ny_udgave.Model.User;

import java.util.List;

public class UserService {

    private UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User registerUser(String email, int userId, String ownerName, String username, String password) {
        User user = new User(email, userId, ownerName, username, password);
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
}
