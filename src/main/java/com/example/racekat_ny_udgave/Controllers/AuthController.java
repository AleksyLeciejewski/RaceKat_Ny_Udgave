package com.example.racekat_ny_udgave.Controllers;

import com.example.racekat_ny_udgave.Services.PetService;
import com.example.racekat_ny_udgave.Services.UserService;

public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

}
