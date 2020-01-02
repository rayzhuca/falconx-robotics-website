package com.falconxrobotics.website.application.projects;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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

    public HashMap<String, List<String>> getAllAttributes(String name) throws IOException {
        if (name == null) {
            return new HashMap<String, List<String>>();
        }
        return sheet.getAllAttributes("project" + name);
    }

    public HashMap<String, Object> getProjectAttributes(String name) throws IOException {
        if (name == null) {
            return new HashMap<String, Object>();
        }
        HashMap<String, List<String>> sheetAttributes = sheet.getAllAttributes("project" + name);

        @SuppressWarnings("unchecked")
        HashMap<String, Object> attributes = sheet.stripSingletonLists( // So I don't need to do: key[0]
                (HashMap<String, List<String>>) sheetAttributes.clone());

        attributes.put("abilities", sheet.zip(sheetAttributes.get("abilityNames"), sheetAttributes.get("abilityScales")));
        attributes.put("compeitions",
                sheet.zipArrays(sheetAttributes.get("compeitionNames").toArray(new String[0]),
                        sheetAttributes.get("compeitionDescriptions").toArray(new String[0]),
                        sheetAttributes.get("compeitionYouTubeLinks").toArray(new String[0])));

        return attributes;
    }
}