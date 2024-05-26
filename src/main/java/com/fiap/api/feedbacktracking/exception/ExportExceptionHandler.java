package com.fiap.api.feedbacktracking.exception;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ExportExceptionHandler {

    public static void handleException(HttpServletResponse response, int statusCode, String message) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            response.getWriter().write("{\"error\": \"" + message + "\"}");
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
