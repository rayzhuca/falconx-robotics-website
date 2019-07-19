package com.falconxrobotics.website.application.home;

import java.io.IOException;
import java.util.HashMap;

import com.falconxrobotics.website.application.googlesheets.ContentGetter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomeComponent {

    private ContentGetter sheet;

    @Autowired
    public HomeComponent(ContentGetter sheet) {
        this.sheet = sheet;
    }

    public HashMap<String, String> getAttributes() throws IOException {
        return sheet.getAttributes("home");
    }
}