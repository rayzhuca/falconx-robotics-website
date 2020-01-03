package com.falconxrobotics.website.application.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.falconxrobotics.website.application.googlesheets.ContentGetter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResourcesComponent {

    private ContentGetter sheet;

    @Autowired
    public ResourcesComponent(ContentGetter sheet) {
        this.sheet = sheet;
    }

    public HashMap<String, List<String>> getAllAttributes(String name) throws IOException {
        if (name == null) {
            return new HashMap<String, List<String>>();
        }
        return sheet.getAllAttributes(name);
    }

    public HashMap<String, Object> getResourcesAttributes(String name) throws IOException {
        if (name == null) {
            return new HashMap<String, Object>();
        }
        HashMap<String, List<String>> sheetAttributes = sheet.getAllAttributes(name);

        @SuppressWarnings("unchecked")
        HashMap<String, Object> attributes = sheet.stripSingletonLists( // So I don't need to do: key[0]
                (HashMap<String, List<String>>) sheetAttributes.clone());

        attributes.put("resources", sheet.zip(sheetAttributes.get("resource names"), sheetAttributes.get("resource links")));
        attributes.put("learnings",
                sheet.zipArrays(sheetAttributes.get("learn item names").toArray(new String[0]),
                        sheetAttributes.get("learn item descriptions").toArray(new String[0]),
                        sheetAttributes.get("learn item image links").toArray(new String[0])));

        return attributes;
    }
}