package com.falconxrobotics.website.application.error;

import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class ErrorComponent {

    public HashMap<String, String> getAttributes(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        return getAttributes(status, message, exception);
    }

    public HashMap<String, String> getAttributes(Object status, Object message, Object exception) {
        HashMap<String, String> attributes = new HashMap<>();

        attributes.put("statuscode", status != null ? status.toString() : "520");
        attributes.put("message", message != null ? message.toString() : null);
        attributes.put("exception", exception != null ? exception.toString() : "");

        if (attributes.get("messsage") == null) {
            try {
                String customMsg = ErrorCode.getMessageFromCode(Integer.parseInt(attributes.get("statuscode")));
                attributes.put("message", customMsg);
            } catch (NumberFormatException nfe) {
                attributes.put("message", "");
                nfe.printStackTrace();
            }
        }

        return attributes;
    }

    private enum ErrorCode {
        FOUR_ZERO_FOUR(404, "Page not found"), FIVE_TWENTY(520, "Unknown error");

        private int code;
        private String message;

        private ErrorCode(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public static String getMessageFromCode(int code) {
            for (ErrorCode err : ErrorCode.values()) {
                if (code == err.getCode()) {
                    return err.getMessage();
                }
            }

            return null;
        }

    }
}