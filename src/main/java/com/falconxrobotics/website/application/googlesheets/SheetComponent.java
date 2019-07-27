package com.falconxrobotics.website.application.googlesheets;

import java.io.IOException;
import java.util.HashMap;

import com.falconxrobotics.website.application.googlesheets.ContentGetter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SheetComponent {

    private ContentGetter sheet;

    @Autowired
    public SheetComponent(ContentGetter sheet) {
        this.sheet = sheet;
    }

    public HashMap<String, String> getAttributes(String sheetTitle) throws IOException {
        if (sheetTitle == null) {
            return new HashMap<String, String>();
        }
        return sheet.getAttributes(sheetTitle);
    }
}