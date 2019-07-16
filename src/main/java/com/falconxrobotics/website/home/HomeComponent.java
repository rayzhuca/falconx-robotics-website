package com.falconxrobotics.website.home;

import java.io.IOException;
import java.util.HashMap;

import com.falconxrobotics.website.googlesheets.ContentGetter;
import com.falconxrobotics.website.googlesheets.GoogleSheetRepository;

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