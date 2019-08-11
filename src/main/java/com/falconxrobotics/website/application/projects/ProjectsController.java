package com.falconxrobotics.website.application.projects;

import java.io.IOException;

import com.falconxrobotics.website.application.googlesheets.SheetComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectsController {

    private SheetComponent sheetComponent;
    private ProjectsComponent projectsComponent;

    @Autowired
    public ProjectsController(SheetComponent sheetComponent, ProjectsComponent projectsComponent) {
        this.sheetComponent = sheetComponent;
        this.projectsComponent = projectsComponent;
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

    // TODO: You know what to do ;)
    @GetMapping(path = { "/project" })
    public String project(Model model, @RequestParam(name = "name") String name) {
        try {
            model.addAllAttributes(projectsComponent.getProjectAttributes(name));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return "error";
        } catch (RuntimeException re) {
            re.printStackTrace();
            // TODO: Let error controllers handle
        }

        return "projects/project";
    }
}
