package com.example.musicdb.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHomePage() {
        return "index";
    }

    @GetMapping("/home")
    public String getIndexPage() {
        return "index";
    }
}
