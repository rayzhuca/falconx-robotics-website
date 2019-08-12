package com.falconxrobotics.website.application.projects;

import java.io.IOException;

import com.falconxrobotics.website.application.error.ErrorComponent;
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
    private ErrorComponent errorComponent;

    @Autowired
    public ProjectsController(SheetComponent sheetComponent, ProjectsComponent projectsComponent,
            ErrorComponent errorComponent) {
        this.sheetComponent = sheetComponent;
        this.projectsComponent = projectsComponent;
        this.errorComponent = errorComponent;
    }

    @GetMapping(path = { "/projects", "/projects/index" })
    public String index(Model model) {
        try {
            model.addAllAttributes(sheetComponent.getAttributes(null));
            return "projects/index";
        } catch (IOException ioe) {
            ioe.printStackTrace();
            // TODO: Let error controllers handle
            return "error";
        } catch (RuntimeException re) {
            re.printStackTrace();
            // TODO: Let error controllers handle
            return "error";
        }
    }

    @GetMapping(path = { "/project" })
    public String project(Model model, @RequestParam(name = "name") String name) {
        try {
            model.addAllAttributes(projectsComponent.getProjectAttributes(name));
            return "projects/project";
        } catch (IOException ioe) {
            model.addAllAttributes(errorComponent.getAttributes("404", null, ioe.getMessage()));
            return "error";
        } catch (RuntimeException re) {
            model.addAllAttributes(errorComponent.getAttributes("404", null, re.getMessage()));
            return "error";
        }
    }
}
