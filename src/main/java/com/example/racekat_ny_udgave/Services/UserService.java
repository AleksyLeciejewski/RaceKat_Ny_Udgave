package com.example.racekat_ny_udgave.Services;

import com.example.racekat_ny_udgave.Infrastructure.Repositories.UserRepo;
import com.example.racekat_ny_udgave.Model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User registerUser(String email, int userId, String password) {
        User user = new User(userId, password, email);
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
    // NYE METODER:
    public User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    public boolean isEmailInUse(String email) {
        return userRepo.findUserByEmail(email) != null;
    }

    public int generateUserId() {
        //Midlertidig impl. fremfor db auto incriment til test
        List<User> allUsers = userRepo.getAllUsers();
        int maxId = 0;
        for (User user : allUsers) {
            if (user.getUserId() > maxId) {
                maxId = user.getUserId();
            }
        }
        return maxId + 1;
    }
}