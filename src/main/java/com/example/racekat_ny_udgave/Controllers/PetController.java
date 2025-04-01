package com.example.racekat_ny_udgave.Controllers;

import com.example.racekat_ny_udgave.Services.PetService;

public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

}
