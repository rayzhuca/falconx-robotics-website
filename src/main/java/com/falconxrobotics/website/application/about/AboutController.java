package com.falconxrobotics.website.application.about;

import java.io.IOException;

import com.falconxrobotics.website.application.googlesheets.SheetComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    private SheetComponent sheetComponent;

    @Autowired
    public AboutController(SheetComponent sheetComponent) {
        this.sheetComponent = sheetComponent;
    }

    @GetMapping("/about/history")
    public String history(Model model) {
        try {
            model.addAllAttributes(sheetComponent.getAttributes(null));
            return "about/history";
        } catch (IOException ioe) {
            ioe.printStackTrace();
            // TODO: Let error controllers handle
        } catch (RuntimeException re) {
            re.printStackTrace();
            // TODO: Let error controllers handle
        }
        return "error";
    }

    @GetMapping("/about/first")
    public String first(Model model) {
        try {
            model.addAllAttributes(sheetComponent.getAttributes(null));
            return "about/first";
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
