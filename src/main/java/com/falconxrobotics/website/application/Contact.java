package com.falconxrobotics.website.application;

import java.io.IOException;

import com.falconxrobotics.website.application.googlesheets.SheetComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Contact {

    private SheetComponent sheetComponent;

    @Autowired
    public Contact(SheetComponent sheetComponent) {
        this.sheetComponent = sheetComponent;
    }

    @GetMapping("/contact")
    public String history(Model model) {
        try {
            model.addAllAttributes(sheetComponent.getAttributes(null));
            return "contact";
        } catch (IOException ioe) {
            ioe.printStackTrace();
            // TODO: Let error controllers handle
        } catch (RuntimeException re) {
            re.printStackTrace();
            // TODO: Let error controllers handle
        }
        return "error";
    }
}