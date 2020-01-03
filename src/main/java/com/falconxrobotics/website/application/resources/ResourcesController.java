package com.falconxrobotics.website.application.resources;

import java.io.IOException;

import com.falconxrobotics.website.application.error.ErrorComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResourcesController {

    private ResourcesComponent resourcesComponent;
    private ErrorComponent errorComponent;

    @Autowired
    public ResourcesController(ResourcesComponent resourcesComponent, ErrorComponent errorComponent) {
        this.resourcesComponent = resourcesComponent;
        this.errorComponent = errorComponent;
    }

    @GetMapping(path = {"/resources/curriculum"})
    public String programming(Model model, @RequestParam(name = "name") String name) {
        try {
            model.addAllAttributes(resourcesComponent.getResourcesAttributes(name));
            return "resources/curriculum/curriculum";
        } catch (IOException ioe) {
            model.addAllAttributes(errorComponent.getAttributes("404", null, ioe.getMessage()));
            return "error";
        } catch (RuntimeException re) {
            re.printStackTrace();
            model.addAllAttributes(errorComponent.getAttributes("404", null, re.getMessage()));
            return "error";
        }
    }
}
