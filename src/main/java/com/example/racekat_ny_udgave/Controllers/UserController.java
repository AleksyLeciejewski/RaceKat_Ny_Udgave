package com.example.racekat_ny_udgave.Controllers;

import com.example.racekat_ny_udgave.Services.UserService;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

}
