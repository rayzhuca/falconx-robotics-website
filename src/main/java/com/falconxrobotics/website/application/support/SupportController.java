package com.falconxrobotics.website.application.support;

import java.io.IOException;

import com.falconxrobotics.website.application.googlesheets.SheetComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SupportController {

    private SheetComponent sheetComponent;

    @Autowired
    public SupportController(SheetComponent sheetComponent) {
        this.sheetComponent = sheetComponent;
    }

    @GetMapping("/support/levels")
    public String history(Model model) {
        try {
            model.addAllAttributes(sheetComponent.getAttributes(null));
            return "support/levels";
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
