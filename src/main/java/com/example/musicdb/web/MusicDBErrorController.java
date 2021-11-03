package com.example.musicdb.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MusicDBErrorController implements ErrorController {

    @RequestMapping(value = "/error")
    public String getErrorPage() {
        return "error";
    }
}
