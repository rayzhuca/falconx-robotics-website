package com.falconxrobotics.website.application.error;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @GetMapping("/error")
    public String index(final HttpServletRequest request, Model model) {
        final Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        final Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        final Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        if (status != null) {
            int code = Integer.parseInt(status.toString());
        }

        return "error";
    }

}