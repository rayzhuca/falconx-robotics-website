package com.falconxrobotics.website.home;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private HomeComponent homeComponent;

    @Autowired
    public HomeController(HomeComponent homeComponent) {
        this.homeComponent = homeComponent;
    }

    @GetMapping("/")
    public String index() {
        // try {
            // model.addAllAttributes(homeComponent.getAttributes());
        // } catch (IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        // }
        return "hi";
    }
}