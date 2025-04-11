package com.example.racekat_ny_udgave.Model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int userId;
    private String ownerName;
    private String username;
    private String password;
    private String email;
    private String role; //User og Admin er vores Roller

    private List<Pet> pets = new ArrayList<>();

    public User(String email, String username, String password) {
        this.email = email;
        this.ownerName = username;
        this.username = username;
        this.password = password;
    }

    public User() {
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(int userId, String password, String email) {
    }


    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
