package com.example.musicdb.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHomePage() {
        return "index";
    }

    @GetMapping("/home")
    public String getIndexPage() {
        return "home";
    }
}
