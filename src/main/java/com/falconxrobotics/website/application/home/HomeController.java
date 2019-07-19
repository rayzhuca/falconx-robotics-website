package com.falconxrobotics.website.application.home;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private HomeComponent homeComponent;

    @Autowired
    public HomeController(HomeComponent homeComponent) {
        this.homeComponent = homeComponent;
        // System.out.println("test");
    }

    @GetMapping(path = { "/index", "/" })
    public String index(Model model) {
        try {
            model.addAllAttributes(homeComponent.getAttributes());
            return "index";
        } catch (IOException ioe) {
            ioe.printStackTrace();
            // TODO: Let error controllers handle
        } catch (RuntimeException re) {
            re.printStackTrace();
            // TODO: Let error controllers handle
        }
        return null; // TODO: Delete this
    }
}
