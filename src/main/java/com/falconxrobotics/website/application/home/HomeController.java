package com.falconxrobotics.website.application.home;

import java.io.IOException;

import com.falconxrobotics.website.application.googlesheets.SheetComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private SheetComponent sheetComponent;

    @Autowired
    public HomeController(SheetComponent sheetComponent) {
        this.sheetComponent = sheetComponent;
        // System.out.println("test");
    }

    @GetMapping(path = { "/index", "/" })
    public String index(Model model) {
        try {
            model.addAllAttributes(sheetComponent.getAttributes("home"));
            return "index";
        } catch (IOException ioe) {
            ioe.printStackTrace();
            // TODO: Let error controllers handle
        } catch (RuntimeException re) {
            re.printStackTrace();
            // TODO: Let error controllers handle
        }
        return "error";
    }
}
