package com.example.racekat_ny_udgave.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class FrontPageController {
    @GetMapping("/")
    public String frontPage() {
        return "index";
    }



}