package com.falconxrobotics.website.application.error;

import java.util.HashMap;

import javax.annotation.Nullable;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class ErrorComponent {

    public HashMap<String, String> getAttributes(@Nullable HttpServletRequest request) {
        if (request == null) {
            return getAttributes(null, null, null);
        }

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        return getAttributes(status, message, exception);
    }

    public HashMap<String, String> getAttributes(@Nullable Object status, @Nullable Object message,
            @Nullable Object exception) {
        HashMap<String, String> attributes = new HashMap<>();

        // set default values
        attributes.put("statuscode", status != null ? status.toString() : "520");
        attributes.put("message", message != null ? message.toString() : null);
        attributes.put("exception", exception != null ? exception.toString() : "");

        try {
            if (!ErrorCode.containsCode(ErrorCode.getCodeFromInt(Integer.parseInt(attributes.get("statuscode"))))) {
                // leave message empty if there is no message for the code
                attributes.replace("message", "");
            } else if (attributes.get("messsage") == null) {
                // put set message of the status code if there was no pre-set message
                try {
                    String customMsg = ErrorCode.getMessageFromCode(Integer.parseInt(attributes.get("statuscode")));
                    attributes.replace("message", customMsg);
                } catch (NumberFormatException nfe) {
                    attributes.replace("message", "");
                    nfe.printStackTrace();
                }
            }
        } catch (NumberFormatException nfe) {
            attributes.replace("message", "");
            nfe.printStackTrace();
        }

        return attributes;
    }

    private enum ErrorCode {
        FOUR_ZERO_FOUR(404, "Page not found"), FIVE_HUNDRED(500, "Internal sever error"),
        FIVE_TWENTY(520, "Unknown error");

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

        public static ErrorCode getCodeFromInt(int code) {
            for (ErrorCode v : values()) {
                if (v.getCode() == code) {
                    return v;
                }
            }
            return FIVE_TWENTY;
        }

        public static boolean containsCode(ErrorCode code) {
            for (ErrorCode v : values()) {
                if (code == v)
                    return true;
            }
            return false;
        }
    }
}