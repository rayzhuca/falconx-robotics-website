package com.falconxrobotics.website.application.projects;

import java.io.IOException;
import java.util.HashMap;

import com.falconxrobotics.website.application.googlesheets.ContentGetter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectsComponent {

    private ContentGetter sheet;

    @Autowired
    public ProjectsComponent(ContentGetter sheet) {
        this.sheet = sheet;
    }

    public HashMap<String, String> getAttributes(String name) throws IOException {
        if (name == null) {
            return new HashMap<String, String>();
        }
        return sheet.getAttributes("project" + name);
    }
}