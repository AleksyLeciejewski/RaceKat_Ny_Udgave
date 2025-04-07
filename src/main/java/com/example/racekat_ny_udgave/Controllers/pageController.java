package com.example.racekat_ny_udgave.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class pageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}