package com.falconxrobotics.website.application.resources;

import java.io.IOException;

import com.falconxrobotics.website.application.googlesheets.SheetComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResourcesController {

    private SheetComponent sheetComponent;

    @Autowired
    public ResourcesController(SheetComponent sheetComponent) {
        this.sheetComponent = sheetComponent;
    }

    @GetMapping(path = {"/resources/curriculum/programming"})
    public String programming(Model model) {
        try {
            model.addAllAttributes(sheetComponent.getAttributes("home"));
            return "/resources/curriculum/programming";
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
