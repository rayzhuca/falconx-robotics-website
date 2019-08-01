package com.falconxrobotics.website.application.projects;

import java.io.IOException;

import com.falconxrobotics.website.application.googlesheets.SheetComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectsController {

    private SheetComponent sheetComponent;

    @Autowired
    public ProjectsController(SheetComponent sheetComponent) {
        this.sheetComponent = sheetComponent;
        System.out.println("yes");
    }

    @GetMapping(path = { "/projects", "/projects/index" })
    public String index(Model model) {
        try {
            model.addAllAttributes(sheetComponent.getAttributes(null));
            return "projects/index";
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
