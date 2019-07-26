package com.falconxrobotics.website.application.about;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    private AboutComponent aboutComponent;

    @Autowired
    public AboutController(AboutComponent aboutComponent) {
        this.aboutComponent = aboutComponent;
    }

    @GetMapping("/about/history")
    public String history(Model model) {
        try {
            model.addAllAttributes(aboutComponent.getAttributes("home"));
            return "about/history";
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
