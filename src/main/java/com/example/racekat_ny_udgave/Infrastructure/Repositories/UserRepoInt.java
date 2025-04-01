package com.example.racekat_ny_udgave.Infrastructure.Repositories;

import com.example.racekat_ny_udgave.Model.User;

import java.util.List;

public interface UserRepoInt {

    List<User> getAllUsers();
    User addUser(User user);
    void deleteUser(User user);
    User getUser(int userId);
    void updateUser(User user);
}
