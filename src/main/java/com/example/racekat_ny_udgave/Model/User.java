package com.example.racekat_ny_udgave.Model;

public class User {

    private int userId;
    private String ownerName;
    private String username;
    private String password;
    private String email;
    private String role; //User og Admin er vores Roller

    public User(String email, int userId, String ownerName, String username, String password) {
        this.userId = userId;
        this.ownerName = ownerName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(){}


    public String getRole() {return role;}

    public void setRole(String role) {this.role = role;}

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

    public User(int userId, String password, String email) {}
}
