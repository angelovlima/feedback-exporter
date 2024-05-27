package com.fiap.api.feedbacktracking.exception;

import java.io.IOException;

public class ExportIOException extends IOException {
    public ExportIOException(String message, IOException e) {
        super(message, e);
    }
}
