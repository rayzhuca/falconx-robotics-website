package com.falconxrobotics.website.home;

import java.io.IOException;
import java.util.HashMap;

import com.falconxrobotics.website.googlesheets.GoogleSheetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomeComponent {

    private GoogleSheetRepository sheet;
    
    @Autowired
    public HomeComponent(GoogleSheetRepository sheet) {
        this.sheet = sheet;
    }

	public HashMap<String, String> getAttributes() throws IOException {
        System.out.println(sheet.get("home", null).toPrettyString());

        return new HashMap<String, String>();
	}
}