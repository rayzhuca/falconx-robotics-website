package com.falconxrobotics.website.application.error;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    private ErrorComponent errorComponent;

    @Autowired
    public CustomErrorController(ErrorComponent errorComponent) {
        this.errorComponent = errorComponent;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @GetMapping("/error")
    public String index(HttpServletRequest request, Model model) {
        model.addAllAttributes(errorComponent.getAttributes(request));

        return "error";
    }

}