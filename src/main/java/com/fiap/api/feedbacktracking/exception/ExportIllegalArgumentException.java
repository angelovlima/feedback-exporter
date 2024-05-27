package com.fiap.api.feedbacktracking.exception;

public class ExportIllegalArgumentException extends IllegalArgumentException {
    public ExportIllegalArgumentException(String message, IllegalArgumentException e) {
        super(message, e);
    }

    public ExportIllegalArgumentException(String message) {
        super(message);
    }
}
