package com.falconxrobotics.website.application.error;

import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class ErrorComponent {

    public HashMap<String, String> getAttributes(HttpServletRequest request) {

        final Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        final Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        final Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        return getAttributes(status, message, exception);

    }

    public HashMap<String, String> getAttributes(Object status, Object message, Object exception) {
        HashMap<String, String> attributes = new HashMap<>();

        attributes.put("statuscode", status != null ? status.toString() : null);
        attributes.put("message", message != null ? message.toString() : null);
        attributes.put("exception", exception != null ? exception.toString() : null);

        return attributes;
    }
}