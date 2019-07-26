package com.falconxrobotics.website.application.about;

import java.io.IOException;
import java.util.HashMap;

import com.falconxrobotics.website.application.googlesheets.ContentGetter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AboutComponent {

    private ContentGetter sheet;

    @Autowired
    public AboutComponent(ContentGetter sheet) {
        this.sheet = sheet;
    }

    public HashMap<String, String> getAttributes(String sheetTitle) throws IOException {
        return sheet.getAttributes(sheetTitle);
    }
}